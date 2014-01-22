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
	
	IservportHomeOperations iservportHomeOperations();
	
}
