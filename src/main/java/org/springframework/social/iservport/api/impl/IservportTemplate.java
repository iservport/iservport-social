package org.springframework.social.iservport.api.impl;

import java.net.URI;
import java.util.Map;

import org.springframework.social.iservport.api.Iservport;
import org.springframework.social.iservport.user.RemoteUser;
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
	
	static final String BASE_URL = "https://api.iservport.com";
	
	private String baseUrl;
	
    public IservportTemplate() {
        super();
    }
    
    public IservportTemplate(String accessToken) {
        super(accessToken);
    }

	@Override
	public String applyGet(String uri) {
		return getRestTemplate().getForObject(buildURI(uri), String.class);
	}

	@Override
	public String applyGet(String uri, Map<String, ?> urlVariables) {
		return getRestTemplate().getForObject(buildURI(uri).toString(), String.class, urlVariables);
	}

	@Override
	public String applyPost(String uri, Object request) {
		return getRestTemplate().postForObject(buildURI(uri), request, String.class);
	}
    
	@Override
	public RemoteUser getProfile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Build the uri.
	 * 
	 * @param uri
	 */
	protected URI buildURI(String uri) {
		return URIBuilder.fromUri(getBaseUrl() + uri).build();
	}
	
	public String getBaseUrl() {
		if (baseUrl==null) {
			return BASE_URL;
		}
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}