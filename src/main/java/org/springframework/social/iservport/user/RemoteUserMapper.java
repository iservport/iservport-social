package org.springframework.social.iservport.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
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
		= "select id, userKey, displayName, profileUrl, imageUrl from core_remote";

	
	/**
	 * INSERT clause for RemoteUser fields.
	 */
	public static final String INSERT_REMOTE_USER
		= "insert into core_remote (userKey, displayName, profileUrl, imageUrl) values (?, ?, ?, ?)";

	
	public RemoteUser mapRow(ResultSet rs, int row) throws SQLException {
		return new RemoteUser(rs.getInt("id"), rs.getString("userKey"), rs.getString("displayName"), rs.getString("profileUrl"), rs.getString("imageUrl"));
	}

}