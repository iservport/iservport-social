package org.springframework.social.iservport.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.iservport.api.Iservport;

/**
 * Iservport API adapter.
 * 
 * @author mauriciofernandesdecastro
 */
public class IservportAdapter 
	implements ApiAdapter<Iservport> 
{

	@Override
	public boolean test(Iservport api) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setConnectionValues(Iservport api, ConnectionValues values) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserProfile fetchUserProfile(Iservport api) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(Iservport api, String message) {
		// TODO Auto-generated method stub

	}

}
