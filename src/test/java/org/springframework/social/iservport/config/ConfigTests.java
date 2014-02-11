package org.springframework.social.iservport.config;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author mauriciofernandesdecastro
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ TestConfig.class, MainConfig.class, SocialConfig.class})
public class ConfigTests {
	
	@Autowired
	private Environment env;
	  
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TextEncryptor textEncryptor;
	
	@Autowired
	private OAuthConnectionDatabaseBootstrap connectionDatabaseBootstrap;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	@Test
	public void test() throws IOException {
		assertNotNull(env.getProperty("security.encryptSalt"));
		assertNotNull(env.getProperty("security.encryptPassword"));
		assertNotNull(passwordEncoder);
		assertNotNull(textEncryptor);
		assertNotNull(connectionDatabaseBootstrap);
		assertNotNull(connectionFactoryLocator);
		assertNotNull(connectionFactoryLocator.getConnectionFactory("iservport"));
		assertNotNull(usersConnectionRepository);
	}

}
