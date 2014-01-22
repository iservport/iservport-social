package org.springframework.social.iservport.api.impl;

import java.util.Map;

import org.springframework.social.iservport.api.IservportHomeOperations;
import org.springframework.web.client.RestOperations;

/**
 * Implementação de IservportHomeOperations.
 * 
 * @author mauriciofernandesdecastro
 */
public class IservportHomeOperationsImpl 
	extends AbstractIservportOperations
	implements IservportHomeOperations
{
	
	/**
	 * Rest constructor.
	 * 
	 * @param restOperations
	 */
	public IservportHomeOperationsImpl(RestOperations restOperations) {
		super(restOperations);
	}

	@Override
	public Map<String, Object> findCategory(int id) {
		return findById(id, "category");
	}

	@Override
	public Map<String, Object> findUser(int id) {
		return findById(id, "user");
	}
	
}
