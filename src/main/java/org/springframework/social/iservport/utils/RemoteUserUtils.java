package org.springframework.social.iservport.utils;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.iservport.api.impl.RemoteUser;

/**
 * Some static helper methods related to RemoteUsers.
 * 
 * @author mauriciofernandesdecastro
 */
public final class RemoteUserUtils {

	/**
	 * Programmatically sign-in the remote user.
	 * 
	 * @param remoteUser
	 */
	public static void signin(RemoteUser remoteUser) {
		SecurityContextHolder.getContext().setAuthentication(authenticationTokenFor(remoteUser));
	}
	
	/**
	 * Construct a Spring Security Authentication token from a RemoteUser object.
	 * 
	 * Useful for treating the RemoteUser as a Principal in Spring Security.
	 * 
	 * @param remoteUser
	 */
	public static Authentication authenticationTokenFor(RemoteUser remoteUser) {
		return new UsernamePasswordAuthenticationToken(remoteUser, null, (Collection<GrantedAuthority>)null);		
	}
	
	/**
	 * Get the currently authenticated principal.
	 */
	public static RemoteUser getCurrentRemoteUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return new RemoteUser();
		}
		Object principal = authentication.getPrincipal();
		return principal instanceof RemoteUser ? (RemoteUser) principal : new RemoteUser();
	}
	
	private RemoteUserUtils() {
	}
	
}
