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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Spring Security encryption Configuration.
 * 
 * <p>
 * In embedded mode, we don't bother with any password encoding or data encryption for developer convenience and ease of developer testing.
 * We also store temporary OAuth sessions in an in-memory ConcurrentHashMap.
 * </p>
 * <p>
 * In standard mode, we apply the Blow Fish Algorithm.
 * </p>
 * 
 * @author Keith Donald
 * @author mauriciofernandesdecastro
 */
@Configuration
@ComponentScan(basePackages = { "org.springframework.social.iservport.user" })
@EnableWebSecurity
public class ClientSecurityConfig 
	extends WebSecurityConfigurerAdapter
{

	@Autowired
	private DataSource dataSource;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

	/**
	 * Allows repositories to access RDBMS data using the JDBC API.
	 */
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public OAuthConnectionDatabaseBootstrap connectionDatabaseBootstrap() {
		return new OAuthConnectionDatabaseBootstrap(dataSource);
	}
	
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
			//Spring Security ignores request to static resources such as CSS or JS files.
			.ignoring()
		    .antMatchers("/webjars/**")
		    .antMatchers("/images/**")
		    .antMatchers("/css/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            //Configures form login
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/authenticate")
                .failureUrl("/login?error=bad_credentials")
            //Configures the logout function
            .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
            //Configures url based authorization
            .and()
                .authorizeRequests()
                    //Anyone can access the urls
                    .antMatchers(
                            "/rest/**", // all rquests with rest are authorized by iservport api
                            "/health/**",
                            "/login",
                            "/login/*",
                            "/signin",
                            "/signin/**",
                            "/signup/**",
                            "/enter/**",
                            "/user/register/**"
                    ).permitAll()
                    //The rest of the our application is protected.
//                    .antMatchers("/**").hasRole("USER")
            //Adds the SocialAuthenticationFilter to Spring Security's filter chain.
	            .and()
	                .apply(new SpringSocialConfigurer());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth
        .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());
    }

}