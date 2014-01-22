package org.springframework.social.iservport.api.impl;

import static org.springframework.social.iservport.api.impl.IservportTemplate.BASE_URL;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestOperations;

/**
 * 
 * @author mauriciofernandesdecastro
 *
 */
public abstract class AbstractIservportOperations {

	protected final RestOperations restOperations;
	
	/**
	 * Rest constructor.
	 */
	public AbstractIservportOperations(RestOperations restOperations) {
		this.restOperations = restOperations;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> findById(int id, String serviceMapping) {
		if (restOperations!=null) {
			URI uri = URIBuilder.fromUri(BASE_URL + "/" + serviceMapping + "/get/" + id).build();
			return restOperations.getForObject(uri, Map.class);
		}
		return new HashMap<>();
	}
}
