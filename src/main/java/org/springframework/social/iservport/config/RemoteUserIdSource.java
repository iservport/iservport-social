package org.springframework.social.iservport.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.UserIdSource;
import org.springframework.social.iservport.api.impl.RemoteUser;
import org.springframework.social.security.SocialUserDetails;

/**
 * User id source based on RemoteUser.
 * 
 * @author mauriciofernandesdecastro
 */
public class RemoteUserIdSource 
	implements UserIdSource
{
	
	private static final Logger logger = LoggerFactory.getLogger(RemoteUserIdSource.class);

	@Override
	public String getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
		Object principal = authentication.getPrincipal();
		String userId = "empty";
		if (principal instanceof RemoteUser) {
			userId = ((RemoteUser) principal).getUserId();
		}
		else if (principal instanceof SocialUserDetails) {
			userId = ((SocialUserDetails) principal).getUserId();
		}
    	logger.debug("User id source has: {}", userId);
		return userId;
	}

}
