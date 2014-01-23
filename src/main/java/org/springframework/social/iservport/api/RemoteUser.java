package org.springframework.social.iservport.api;

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
	
	private Integer id;
	
	private String displayName;
	
	private String profileUrl;
	
	private String imageUrl;
	
	/**
	 * Constructor.
	 */
	public RemoteUser() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
