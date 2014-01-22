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
		Object user = api.iservportHomeOperations().findUser(0);
//		values.setProviderUserId(Long.toString(user.getId()));
//        values.setDisplayName(user.getDisplayName());
//        values.setProfileUrl(user.getProfileUrl());
//        values.setImageUrl(user.getProfileImageUrl());
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
