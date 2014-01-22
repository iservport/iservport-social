package org.springframework.social.iservport.api.impl;

import org.springframework.social.iservport.api.Iservport;
import org.springframework.social.iservport.api.IservportHomeOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * Iservport template.
 * 
 * @author mauriciofernandesdecastro
 */
public class IservportTemplate 
	extends AbstractOAuth2ApiBinding 
	implements Iservport 
{
	
	static final String BASE_URL = "https://api.iservport.com/rest/";
	
	private IservportHomeOperations iservportHomeOperations;
	
    public IservportTemplate() {
        super();
    }
    
    public IservportTemplate(String accessToken) {
        super(accessToken);
        init();
    }
    
    private void init() {
    	iservportHomeOperations = new IservportHomeOperationsImpl(getRestTemplate());
	}

	@Override
	public IservportHomeOperations iservportHomeOperations() {
		return iservportHomeOperations;
	}
    
}