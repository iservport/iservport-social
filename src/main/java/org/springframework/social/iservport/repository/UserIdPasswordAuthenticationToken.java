package org.springframework.social.iservport.repository;

import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.iservport.api.impl.RemoteUser;

/**
 * Override UsernamePasswordAuthenticationToken to provide userId as user id.
 * 
 * @author mauriciofernandesdecastro
 *
 */
public class UserIdPasswordAuthenticationToken 
	extends UsernamePasswordAuthenticationToken
{

	private static final long serialVersionUID = 1L;

	/**
	 * Custom constructor.
	 * 
	 * @param principal
	 */
	public UserIdPasswordAuthenticationToken(RemoteUser principal) {
		super(principal, null, Arrays.asList((GrantedAuthority) new SimpleGrantedAuthority("ROLE_USER")));
	}
	
	@Override
	public String getName() {
		return ((RemoteUser) getPrincipal()).getUserId();
	}

}
