package org.springframework.social.iservport.user;

import java.io.Serializable;

import org.springframework.social.connect.ConnectionValues;

/**
 * Representation of a remote user.
 * 
 * @author mauriciofernandesdecastro
 */
public class RemoteUser 
	implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id = new Integer(0);
	
	private String userKey = "";
	
	private String displayName = "";
	
	private String profileUrl = "";
	
	private String imageUrl = "";
	
	/**
	 * Constructor.
	 */
	public RemoteUser() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param userKey
	 * @param displayName
	 * @param profileUrl
	 * @param imageUrl
	 */
	public RemoteUser(Integer id, String userKey, String displayName, String profileUrl, String imageUrl) {
		this();
		setId(id);
		setUserKey(userKey);
		setDisplayName(displayName);
		setProfileUrl(profileUrl);
		setImageUrl(imageUrl);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public void setConnectionValues(ConnectionValues values) {
		values.setProviderUserId(String.valueOf(id));
		values.setDisplayName(displayName);
		values.setProfileUrl(profileUrl);
		values.setImageUrl(imageUrl);
	}

}
