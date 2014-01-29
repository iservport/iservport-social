/*
 * Copyright 2010 the original author or authors.
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
package org.springframework.social.iservport.connect;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.iservport.user.RemoteUser;

/**
 * Some static helper methods related to the remote user.
 * 
 * @author mauriciofernandesdecastro
 */
public final class SecurityUtils {

	/**
	 * Programmatically sign-in the member holding the provided remote user.
	 * 
	 * @param remoteUser
	 */
	public static void signin(RemoteUser remoteUser) {
		SecurityContextHolder.getContext().setAuthentication(authenticationTokenFor(remoteUser));
	}
	
	/**
	 * Construct a Spring Security Authentication token from an RemoteUser object.
	 * Useful for treating the RemoteUser as a Principal in Spring Security.
	 * 
	 * @param remoteUser
	 */
	public static Authentication authenticationTokenFor(RemoteUser remoteUser) {
		return new UsernamePasswordAuthenticationToken(remoteUser, null, (Collection<GrantedAuthority>)null);		
	}
	
	/**
	 * Get the currently authenticated RemoteUser principal.
	 */
	public static RemoteUser getCurrentAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		return principal instanceof RemoteUser ? (RemoteUser) principal : null;
	}
	
	private SecurityUtils() {
	}
	
}
