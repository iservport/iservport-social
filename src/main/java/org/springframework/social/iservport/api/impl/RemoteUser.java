package org.springframework.social.iservport.api.impl;

import java.io.Serializable;

import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.iservport.api.ProviderType;

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
	
	private String firstName = "";
	
	private String lastName = "";
	
	private String displayName = "";
	
	private String profileUrl = "";
	
	private String password = "";
	
	private String imageUrl = "";
	
	private String roles = "";
	
	private ProviderType providerType = ProviderType.iservport;
	
	/**
	 * Constructor.
	 */
	public RemoteUser() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param userProfile
	 */
	public RemoteUser(UserProfile userProfile) {
		this();
		this.firstName = userProfile.getFirstName();
		this.lastName = userProfile.getLastName();
		this.userKey = userProfile.getEmail();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param userKey
	 * @param firstName
	 * @param lastName
	 * @param displayName
	 * @param profileUrl
	 * @param imageUrl
	 * @param password
	 * @param roles
	 * @param providerType
	 */
	public RemoteUser(Integer id, String userKey, String firstName, String lastName, 
			String displayName, String profileUrl, String imageUrl, String password,
			String roles, ProviderType providerType) {
		this();
		setId(id);
		setUserKey(userKey);
		setFirstName(firstName);
		setLastName(lastName);
		setDisplayName(displayName);
		setProfileUrl(profileUrl);
		setImageUrl(imageUrl);
		setPassword(password);
		setRoles(roles);
		setProviderType(providerType);
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
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public ProviderType getProviderType() {
		return providerType;
	}
	public void setProviderType(ProviderType providerType) {
		this.providerType = providerType;
	}

}
