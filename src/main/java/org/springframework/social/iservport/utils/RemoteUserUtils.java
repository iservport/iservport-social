package org.springframework.social.iservport.utils;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.iservport.api.impl.RemoteUser;

/**
 * Some static helper methods related to RemoteUsers.
 * 
 * @author mauriciofernandesdecastro
 */
public final class RemoteUserUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(RemoteUserUtils.class);

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
		List<GrantedAuthority> roles = Arrays.asList((GrantedAuthority) new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(remoteUser, null, roles);		
	}
	
	/**
	 * Get the currently authenticated user key.
	 */
	public static String getCurrentRemoteUserKey() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			logger.warn("Authentication not found.");
			return "void";
		}
		Object principal = authentication.getPrincipal();
		logger.debug("Currently authenticated principal: [{}]", principal);
		return principal instanceof RemoteUser ? ((RemoteUser) principal).getUserId() : "empty";
	}
	
	private RemoteUserUtils() {
	}
	
}
