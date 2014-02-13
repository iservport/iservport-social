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

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.iservport.api.Iservport;
import org.springframework.social.iservport.api.impl.IservportTemplate;
import org.springframework.social.iservport.api.impl.RemoteUser;
import org.springframework.social.iservport.connect.IservportConnectionFactory;
import org.springframework.social.iservport.repository.RemoteUserRepository;
import org.springframework.social.iservport.utils.RemoteUserUtils;

/**
 * Spring Social Configuration.
 *
 * @author mauriciofernandesdecastro
 */
@Configuration
@EnableSocial
@Import({ MainConfig.class })
public class SocialConfig 
	implements SocialConfigurer 
{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Environment env;

	@Autowired
	private RemoteUserRepository remoteUserRepository;

	@Autowired
	private TextEncryptor textEncryptor;

	/**
	 * Create database if necessary.
	 */
	@Bean
	public OAuthConnectionDatabaseBootstrap connectionDatabaseBootstrap() {
		return new OAuthConnectionDatabaseBootstrap(dataSource);
	}
	
	/**
	 * Configure iservport connection factory.
	 */
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment env) {
        configurer.addConnectionFactory(new IservportConnectionFactory(env.getProperty("iservport.api.url")));
    }

	/**
	 * Configure jdbc connection repository.
	 */
	@Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor);
		usersConnectionRepository.setTablePrefix("core_");
//		usersConnectionRepository.setConnectionSignUp(new SimpleConnectionSignUp());
		return usersConnectionRepository;
    }

	/**
	 * Configure the user id.
	 */
    @Override
    public UserIdSource getUserIdSource() {
		final RemoteUser remoteUser = RemoteUserUtils.getCurrentRemoteUser();
		if (remoteUser == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
        return new UserIdSource() {
			@Override
			public String getUserId() {
				return remoteUser.getId().toString();
			}
        };
    }
    
	/**
	 * A request-scoped bean representing the API binding to ISERVPORT for the current user.
	 * 
	 * Since it is a scoped-proxy, references to this bean MAY be injected at application startup time.
	 */
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Iservport iservport(ConnectionRepository repository) {
		Connection<Iservport> connection = repository.findPrimaryConnection(Iservport.class);
		if (connection != null) {
			Iservport iservport = connection.getApi();
			((IservportTemplate) iservport).setBaseUrl(env.getProperty("iservport.api.url"));
			return iservport;
		}
		return null;
	}

}
