package org.springframework.social.iservport.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.social.iservport.api.ProviderType;
import org.springframework.social.iservport.api.impl.RemoteUser;
import org.springframework.stereotype.Component;

/**
 * RowMapper that maps a row in a ResultSet to a RemoteUser object instance.
 * 
 * @author mauriciofernandesdecastro
 */
@Component
public class RemoteUserMapper 
	implements RowMapper<RemoteUser> 
{

	/**
	 * SELECT clause for RemoteUser fields.
	 */
	public static final String SELECT_REMOTE_USER
		= "select id, userKey, firstName, lastName, displayName, profileUrl, imageUrl, password, roles, providerType from core_RemoteUser";

	
	/**
	 * INSERT clause for RemoteUser fields.
	 */
	public static final String INSERT_REMOTE_USER
		= "insert into core_RemoteUser (userKey, firstName, lastName, displayName, profileUrl, imageUrl, password, roles, providerType) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	
	public RemoteUser mapRow(ResultSet rs, int row) throws SQLException {
		return new RemoteUser(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("userKey"), rs.getString("displayName"), rs.getString("profileUrl"), rs.getString("imageUrl"), rs.getString("password"), rs.getString("roles"), ProviderType.valueOf(rs.getString("providerType")));
	}

}
