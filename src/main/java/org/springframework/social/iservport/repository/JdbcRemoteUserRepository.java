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
import org.springframework.social.connect.UserProfile;
import org.springframework.social.iservport.api.ProviderType;
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
	public RemoteUser createRemoteUser(UserProfile userProfile, 
			String displayName, String profileUrl, String imageUrl, String password, String roles,
			ProviderType providerType) throws UserKeyAlreadyOnFileException {
		try {
			jdbcTemplate.update(RemoteUserMapper.INSERT_REMOTE_USER, userProfile.getEmail(), displayName, profileUrl
					, imageUrl);
			Integer id = jdbcTemplate.queryForInt("call identity()");
			return new RemoteUser(id, userProfile.getEmail(), userProfile.getFirstName(), userProfile.getLastName()
					, displayName, profileUrl, imageUrl, password, roles, providerType);
		} catch (DuplicateKeyException e) {
			throw new UserKeyAlreadyOnFileException(userProfile.getEmail());
		}
	}

	@Transactional
	public RemoteUser createRemoteUser(String userKey, String firstName, String lastName, 
			String displayName, String profileUrl, String imageUrl, String password, String roles,
			ProviderType providerType) throws UserKeyAlreadyOnFileException {
		try {
			jdbcTemplate.update(RemoteUserMapper.INSERT_REMOTE_USER, userKey, displayName, profileUrl, imageUrl);
			Integer id = jdbcTemplate.queryForInt("call identity()");
			return new RemoteUser(id, userKey, firstName, lastName, displayName, profileUrl, imageUrl
					, password, roles, providerType);
		} catch (DuplicateKeyException e) {
			throw new UserKeyAlreadyOnFileException(userKey);
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

	private static final String SELECT_PASSWORD_PROTECTED_ACCOUNT = "select id, firstName, lastName, email, password, username, gender, pictureSet from Member";


}
