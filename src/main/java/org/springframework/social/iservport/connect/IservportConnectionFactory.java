package org.springframework.social.iservport.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.iservport.api.Iservport;
import org.springframework.social.iservport.utils.RemoteUserUtils;

/**
 * Iservport connection factory.
 * 
 * @author mauriciofernandesdecastro
 */
public class IservportConnectionFactory extends OAuth2ConnectionFactory<Iservport> {
	
    public IservportConnectionFactory() {
        super("iservport", new IservportServiceProvider(RemoteUserUtils.getCurrentRemoteUser()), new IservportAdapter());
    }
    
}
