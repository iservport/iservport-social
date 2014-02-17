package org.springframework.social.iservport.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.iservport.api.impl.RemoteUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * RemoteUserRepository implementation that stores RemoteUser's in a relational database using the JDBC API.
 * 
 * @author mauriciofernandesdecastro
 */
@Repository
public class JdbcRemoteUserRepository 
	implements RemoteUserRepository 
{

	private final JdbcTemplate jdbcTemplate;

	private final PasswordEncoder passwordEncoder;

	private final RemoteUserMapper remoteUserMapper;

	/**
	 * Constructor.
	 * 
	 * @param jdbcTemplate
	 * @param passwordEncoder
	 * @param remoteUserMapper
	 */
	@Autowired
	public JdbcRemoteUserRepository(DataSource dataSource, PasswordEncoder passwordEncoder, RemoteUserMapper remoteUserMapper) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.passwordEncoder = passwordEncoder;
		this.remoteUserMapper = remoteUserMapper;
	}

	@Transactional
	public RemoteUser createRemoteUser(RemoteUser remoteUser) throws UserKeyAlreadyOnFileException {
		try {
			jdbcTemplate.update(RemoteUserMapper.INSERT_REMOTE_USER, getOrderedFields(remoteUser));
			return remoteUser;
		} catch (DuplicateKeyException e) {
			System.out.println(e);
			throw new UserKeyAlreadyOnFileException(remoteUser.getUserKey());
		}
	}

	public RemoteUser findById(int id) {
		return jdbcTemplate.queryForObject(RemoteUserMapper.SELECT_REMOTE_USER + " where id = ?", remoteUserMapper, id);
	}

	public RemoteUser findByUserKey(String userKey) throws UserKeyNotFoundException {
		try {
			return jdbcTemplate.queryForObject(RemoteUserMapper.SELECT_REMOTE_USER + " where userKey = ?", remoteUserMapper, userKey);
		} catch (EmptyResultDataAccessException e) {
			throw new UserKeyNotFoundException(userKey);
		}
	}

	public RemoteUser authenticate(String userKey, String password) throws UserKeyNotFoundException, InvalidPasswordException {
		try {
			return jdbcTemplate.queryForObject(RemoteUserMapper.SELECT_REMOTE_USER + " where userKey = ?", passwordProtectedRemoteUserMapper, userKey).accessRemoteUser(password, passwordEncoder);
		} catch (EmptyResultDataAccessException e) {
			throw new UserKeyNotFoundException(userKey);
		}
	}

	private RowMapper<PasswordProtectedRemoteUser> passwordProtectedRemoteUserMapper = new RowMapper<PasswordProtectedRemoteUser>() {
		public PasswordProtectedRemoteUser mapRow(ResultSet rs, int row) throws SQLException {
			RemoteUser remoteUser = remoteUserMapper.mapRow(rs, row);
			return new PasswordProtectedRemoteUser(remoteUser, rs.getString("password"));
		}
	};

	private static class PasswordProtectedRemoteUser {

		private RemoteUser remoteUser;

		private String encodedPassword;

		public PasswordProtectedRemoteUser(RemoteUser remoteUser, String encodedPassword) {
			this.remoteUser = remoteUser;
			this.encodedPassword = encodedPassword;
		}

		public RemoteUser accessRemoteUser(String password, PasswordEncoder passwordEncoder) throws InvalidPasswordException {
			if (passwordEncoder.matches(password, encodedPassword)) {
				return remoteUser;
			} else {
				throw new InvalidPasswordException();
			}
		}

	}
	
	/**
	 * Helper method to obtain field contents in the right sequence.
	 * 
	 * @param remoteUser
	 */
	private Object[] getOrderedFields(RemoteUser remoteUser) {
		return new Object[] {
				remoteUser.getId()
			,remoteUser.getUserKey()
			,remoteUser.getFirstName()
			,remoteUser.getLastName()
			,remoteUser.getDisplayName()
			,remoteUser.getProfileUrl()
			,remoteUser.getImageUrl()
			,remoteUser.getRoles()
			,remoteUser.getProviderTypeAsString()
		};
	}

}
