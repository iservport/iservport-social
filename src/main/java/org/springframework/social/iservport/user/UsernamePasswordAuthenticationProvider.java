package org.springframework.social.iservport.user;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * Adapts {@link RemoteUserRepository#authenticate(String, String)} to the SpringSecurity AuthenticationProvider SPI.
 * 
 * Allows the RemoteUserRepository to drive authentication in a Spring Security environment.
 * 
 * The authenticated RemoteUser is treated as the {@link Authentication#getPrincipal() Authentication Principal}.
 * 
 * @author Keith Donald
 * @author mauriciofernandesdecastro
 */
@Service
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

	private RemoteUserRepository remoteUserRepository;
	
	@Inject
	public UsernamePasswordAuthenticationProvider(RemoteUserRepository accountRepository) {
		this.remoteUserRepository = accountRepository;
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		try {
			RemoteUser account = remoteUserRepository.authenticate(token.getName(), (String) token.getCredentials());
			return authenticatedToken(account, authentication);
		} catch (UserKeyNotFoundException e) {
			throw new org.springframework.security.core.userdetails.UsernameNotFoundException(token.getName(), e);
		} catch (InvalidPasswordException e) {
			throw new BadCredentialsException("Invalid password", e);
		}
	}

	public boolean supports(Class<? extends Object> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}
	
	// internal helpers
	
	private Authentication authenticatedToken(RemoteUser account, Authentication original) {
		List<GrantedAuthority> authorities = null;
		UsernamePasswordAuthenticationToken authenticated = new UsernamePasswordAuthenticationToken(account, null, authorities);
		authenticated.setDetails(original.getDetails());
		return authenticated;		
	}

}