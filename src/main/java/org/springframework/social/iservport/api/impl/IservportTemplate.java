package org.springframework.social.iservport.api.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.social.iservport.api.Iservport;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.URIBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Iservport template.
 * 
 * @author mauriciofernandesdecastro
 */
public class IservportTemplate 
	extends AbstractOAuth2ApiBinding 
	implements Iservport 
{
	
	private static final String DEFAULT_BASE_URL = "https://api.iservport.com";
	
	private String baseUrl;
	
	/**
	 * Access token template constructor.
	 * 
	 * @param accessToken
	 */
    public IservportTemplate(String accessToken) {
        this(accessToken, DEFAULT_BASE_URL);
    }

    /**
	 * Access token template and base url constructor.
     * 
     * @param accessToken
     * @param baseUrl
     */
    public IservportTemplate(String accessToken, String baseUrl) {
        super(accessToken);
        setBaseUrl(baseUrl);
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
		RemoteUser remoteUser = new RemoteUser();
		String userMap = getRestTemplate().getForObject(buildURI("/rest/adm/user/get"), String.class);
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<?,?> userAsMap = mapper.readValue(userMap, Map.class);
			remoteUser.setId((Integer) userAsMap.get("id"));
			remoteUser.setUserKey((String) userAsMap.get("userKey"));
			remoteUser.setFirstName((String) userAsMap.get("userFirstName"));
			remoteUser.setLastName((String) userAsMap.get("userLastName"));
			remoteUser.setDisplayName((String) userAsMap.get("displayName"));
			remoteUser.setImageUrl((String) userAsMap.get("imageUrl"));
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to read profile.");
		}
		return remoteUser;
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
			throw new IllegalArgumentException();
		}
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}