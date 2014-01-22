package org.springframework.social.iservport.connect;

import java.util.List;

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
	
    public IservportTemplate() {
        super();
    }
    
    public IservportTemplate(String accessToken) {
        super(accessToken);
    }

	@SuppressWarnings("unused")
	@Override
	public IservportHomeOperations iservportHomeOperations() {
		List<?> stateList = (List<?>) getRestTemplate().getForEntity("/rest/state/find", List.class, 1);
		return null;
	}
    
}