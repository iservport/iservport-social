package org.springframework.social.iservport.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

	private final RemoteUserMapper remoteUserMapper;

	/**
	 * Constructor.
	 * 
	 * @param jdbcTemplate
	 * @param passwordEncoder
	 * @param remoteUserMapper
	 */
	@Autowired
	public JdbcRemoteUserRepository(JdbcTemplate jdbcTemplate, RemoteUserMapper remoteUserMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.remoteUserMapper = remoteUserMapper;
	}

	@Transactional
	public RemoteUser createRemoteUser(String userKey, String displayName, String profileUrl, String imageUrl) throws UserKeyAlreadyOnFileException {
		try {
			jdbcTemplate.update(RemoteUserMapper.INSERT_REMOTE_USER, userKey, displayName, profileUrl, imageUrl);
			Integer id = jdbcTemplate.queryForInt("call identity()");
			return new RemoteUser(id, userKey, displayName, profileUrl, imageUrl);
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

}