package org.springframework.social.iservport.connect;

import org.springframework.social.iservport.api.Iservport;
import org.springframework.social.iservport.api.impl.IservportTemplate;
import org.springframework.social.iservport.user.RemoteUser;
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

	/**
	 * Constructor.
	 * 
	 * @param remoteUser
	 * @param applicationUrl
	 */
    public IservportServiceProvider(RemoteUser remoteUser, String applicationUrl) {
        super(new OAuth2Template(remoteUser.getId().toString(), "",
        		applicationUrl+"/rest/oauth/authorize",
        		applicationUrl+"/rest/oauth/token"));
    }

    public Iservport getApi(String accessToken) {
        return new IservportTemplate(accessToken);
    }

}