package org.springframework.social.iservport.api;

import java.util.Map;

/**
 * Home operations.
 * 
 * @author mauriciofernandesdecastro
 */
public interface IservportHomeOperations {
	
	// core
	
	Map<String, Object> findCategory(int id);
	
	// user

	Map<String, Object> findUser(int id);
}
