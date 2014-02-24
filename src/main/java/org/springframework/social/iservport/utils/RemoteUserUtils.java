package org.springframework.social.iservport.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.iservport.api.impl.RemoteUser;
import org.springframework.social.iservport.repository.UserIdPasswordAuthenticationToken;

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
		SecurityContextHolder.getContext().setAuthentication(new UserIdPasswordAuthenticationToken(remoteUser));
	}
	
	/**
	 * Get the currently authenticated user key.
	 */
	public static String getCurrentRemoteUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			logger.warn("Authentication not found.");
			return "void";
		}
		Object principal = authentication.getPrincipal();
		String userId = principal instanceof RemoteUser ? ((RemoteUser) principal).getUserId() : "empty";
		logger.debug("Currently authenticated userId: [{}]", userId);
		return userId;
	}
	
	private RemoteUserUtils() {
	}
	
}
