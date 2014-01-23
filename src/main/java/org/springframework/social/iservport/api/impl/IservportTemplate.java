package org.springframework.social.iservport.api.impl;

import org.springframework.social.iservport.api.Iservport;
import org.springframework.social.iservport.api.RemoteUser;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.URIBuilder;

/**
 * Iservport template.
 * 
 * @author mauriciofernandesdecastro
 */
public class IservportTemplate 
	extends AbstractOAuth2ApiBinding 
	implements Iservport 
{
	
	static final String BASE_URL = "http://api.iservport.com:8080/rest";
	
    public IservportTemplate() {
        super();
    }
    
    public IservportTemplate(String accessToken) {
        super(accessToken);
    }

	@Override
	public String applyGet(String uri) {
		return getRestTemplate().getForObject(URIBuilder.fromUri(BASE_URL + uri).build(), String.class);
	}

	@Override
	public String applyPost(String uri, Object request) {
		return getRestTemplate().postForObject(URIBuilder.fromUri(BASE_URL + uri).build(), request, String.class);
	}
    
	@Override
	public RemoteUser getProfile() {
		// TODO Auto-generated method stub
		return null;
	}

}