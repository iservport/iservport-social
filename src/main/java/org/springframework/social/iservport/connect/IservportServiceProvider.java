package org.springframework.social.iservport.connect;

import org.springframework.social.iservport.api.Iservport;
import org.springframework.social.iservport.api.impl.IservportTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Iservport service provider.
 * 
 * @author mauriciofernandesdecastro
 */
public final class IservportServiceProvider 
	extends AbstractOAuth2ServiceProvider<Iservport> 
{

    public IservportServiceProvider(String clientId, String clientSecret) {
        super(new OAuth2Template(clientId, clientSecret,
            "https://api.iservport.com/rest/oauth/authorize",
            "https://api.iservport.com/rest/oauth/token"));
    }

    public Iservport getApi(String accessToken) {
        return new IservportTemplate(accessToken);
    }

}