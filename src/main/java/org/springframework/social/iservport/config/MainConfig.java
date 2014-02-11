package org.springframework.social.iservport.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = { "org.springframework.social.iservport.repository" })
public class MainConfig {

	@Autowired
	private Environment env;

	/**
	 * Blow fish algorithm password encoder.
	 */
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.queryableText(env.getProperty("security.encryptPassword", "password"), env.getProperty("security.encryptSalt", "00"));
	}

}
