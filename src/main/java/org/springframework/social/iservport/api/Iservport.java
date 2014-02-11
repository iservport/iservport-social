package org.springframework.social.iservport.api;

import java.util.Map;

import org.springframework.social.ApiBinding;
import org.springframework.social.iservport.api.impl.RemoteUser;

/**
 * Iservpor API binding.
 * 
 * @author mauriciofernandesdecastro
 *
 */
public interface Iservport extends ApiBinding
{
	
	/**
	 * Common API call
	 * 
	 * @param uri
	 */
	String applyGet(String uri);
	
	/**
	 * Common API call
	 * 
	 * @param uri
	 * @param urlVariables
	 */
	String applyGet(String uri, Map<String, ?> urlVariables);
	
	/**
	 * Common API call
	 * 
	 * @param uri
	 * @param request
	 */
	String applyPost(String uri, Object request);
	
	/**
	 * The user profile.
	 */
	RemoteUser getProfile();
	
}
