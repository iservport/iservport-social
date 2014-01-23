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
	
	static final String BASE_URL = "https://api.iservport.com/rest/";
	
    public IservportTemplate() {
        super();
    }
    
    public IservportTemplate(String accessToken) {
        super(accessToken);
    }

	@Override
	public Object apply(String uri) {
		return getRestTemplate().getForObject(URIBuilder.fromUri(BASE_URL + uri).build(), Object.class);
	}

	@Override
	public RemoteUser getProfile() {
		// TODO Auto-generated method stub
		return null;
	}
    
}