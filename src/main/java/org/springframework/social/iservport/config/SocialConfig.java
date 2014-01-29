/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.iservport.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.iservport.api.Iservport;
import org.springframework.social.iservport.api.impl.IservportTemplate;
import org.springframework.social.iservport.connect.IservportConnectionFactory;
import org.springframework.social.iservport.connect.RemoteUserSignInAdapter;
import org.springframework.social.iservport.user.RemoteUser;
import org.springframework.social.iservport.user.RemoteUserRepository;
import org.springframework.social.iservport.utils.RemoteUserUtils;

/**
 * Spring Social Configuration.
 *
 * @author Keith Donald
 * @author mauriciofernandesdecastro
 */
@Configuration
public class SocialConfig {

	@Inject
	private DataSource dataSource;
	
	@Inject
	private TextEncryptor textEncryptor;

	@Inject
	private Environment environment;
	
	@Bean
	public OAuthConnectionDatabaseBootstrap connectionDatabaseBootstrap() {
		return new OAuthConnectionDatabaseBootstrap(dataSource);
	}
	
	/**
	 * The locator for SaaS provider connection factories.
	 * 
	 * When support for a new provider is added to Greenhouse, simply register the corresponding {@link ConnectionFactory} here.
	 * The current Environment is used to lookup the credentials assigned to the Greenhouse application by each provider during application registration.
	 * 
	 * This bean is defined as a scoped-proxy so it can be serialized in support of {@link ProviderSignInAttempt provier sign-in attempts}.
	 */
	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES)	
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
//		registry.addConnectionFactory(new TwitterConnectionFactory(environment.getProperty("twitter.consumerKey"), environment.getProperty("twitter.consumerSecret")));
//		registry.addConnectionFactory(new FacebookConnectionFactory(environment.getProperty("facebook.appId"), environment.getProperty("facebook.appSecret")));
//		registry.addConnectionFactory(new LinkedInConnectionFactory(environment.getProperty("linkedin.consumerKey"), environment.getProperty("linkedin.consumerSecret")));		
		registry.addConnectionFactory(new IservportConnectionFactory(environment.getProperty("iservport.consumerKey"), environment.getProperty("iservport.consumerSecret")));
		return registry;
	}
	
	/**
	 * The shared store for users' connection information.
	 * 
	 * Uses a RDBMS-based store accessed with Spring's JdbcTemplate.
	 * The returned repository encrypts the data using the configured {@link TextEncryptor}.
	 * 
	 * This bean is defined as a scoped-proxy so it can be serialized in support of {@link ProviderSignInAttempt provier sign-in attempts}.
	 */
	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES)
	public UsersConnectionRepository usersConnectionRepository() {
		JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), textEncryptor);
		usersConnectionRepository.setTablePrefix("core_");
		return usersConnectionRepository;
	}

	/**
	 * A request-scoped bean that provides the data access interface to the current user's connections.
	 * 
	 * Since it is a scoped-proxy, references to this bean MAY be injected at application startup time.
	 * If no remote user is authenticated when the target is resolved, an {@link IllegalStateException} is thrown.
	 * 
	 * @throws IllegalStateException when no user is authenticated.
	 */
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public ConnectionRepository connectionRepository() {
		RemoteUser remoteUser = RemoteUserUtils.getCurrentAccount();
		if (remoteUser == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
		return usersConnectionRepository().createConnectionRepository(remoteUser.getId().toString());
	}

	/**
	 * A request-scoped bean representing the API binding to ISERVPORT for the current user.
	 * 
	 * Since it is a scoped-proxy, references to this bean MAY be injected at application startup time.
	 */
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Iservport iservport() {
		Connection<Iservport> iservport = connectionRepository().findPrimaryConnection(Iservport.class);
		return iservport != null ? iservport.getApi() : new IservportTemplate();
	}

	/**
	 * The Spring MVC Controller that coordinates connections to service providers on behalf of users.
	 */
	@Bean
    public ConnectController connectController() {
        return new ConnectController(connectionFactoryLocator(), connectionRepository());
    }

	/**
	 * The Spring MVC Controller that coordinates "sign-in with {provider}" attempts.
	 * 
	 * @param remoteUserRepository
	 * @param requestCache
	 */
	@Bean
	public ProviderSignInController providerSignInController(RemoteUserRepository remoteUserRepository, RequestCache requestCache) {
		return new ProviderSignInController(connectionFactoryLocator(), usersConnectionRepository(), new RemoteUserSignInAdapter(remoteUserRepository, requestCache));
	}
	
}
