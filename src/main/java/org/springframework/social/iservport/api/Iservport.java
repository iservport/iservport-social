package org.springframework.social.iservport.api;

import org.springframework.social.ApiBinding;

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
	 * @param request
	 */
	String applyPost(String uri, Object request);
	
	RemoteUser getProfile();
	
}
