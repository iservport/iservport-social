package org.springframework.social.iservport.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.iservport.api.Iservport;

/**
 * Iservport connection factory.
 * 
 * @author mauriciofernandesdecastro
 */
public class IservportConnectionFactory extends OAuth2ConnectionFactory<Iservport> {
	
    public IservportConnectionFactory(String clientId, String clientSecret) {
        super("iservport", new IservportServiceProvider(clientId, clientSecret), new IservportAdapter());
    }
    
}
