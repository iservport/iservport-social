package org.springframework.social.iservport.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Create UserConnection table if does not exist.
 * 
 * @author mauriciofernandesdecastro
 */
public class OAuthConnectionDatabaseBootstrap 
	implements InitializingBean
{
	
	private static final Logger logger = LoggerFactory.getLogger(OAuthConnectionDatabaseBootstrap.class);
	
	private DataSource dataSource;
	
	/**
	 * Data source constructor.
	 * 
	 * @param dataSource
	 */
	public OAuthConnectionDatabaseBootstrap(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	private String tableCreate = "create table core_UserConnection (userId varchar(255) not null,"
			+ "providerId varchar(255) not null,"
			+ "providerUserId varchar(255),"
			+ "rank int not null,"
			+ "displayName varchar(255),"
			+ "profileUrl varchar(512),"
			+ "imageUrl varchar(512),"
			+ "accessToken varchar(255) not null,"
			+ "secret varchar(255),"
			+ "refreshToken varchar(255),"
			+ "expireTime bigint,"
			+ "primary key (userId, providerId, providerUserId));";
	
	private String tableIndexCreate = "create unique index UserConnectionRank on UserConnection(userId, providerId, rank);";

	@Override
	public void afterPropertiesSet() throws Exception {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			DatabaseMetaData metadata = connection.getMetaData();
			ResultSet result = metadata.getTables(null, null, "core_UserConnection", null);
			try {
				// checks if the table exists, if not, creates it
				if (result.next()) {
					logger.info("User connection table found.");
				}
				else {
					Statement stmt = connection.createStatement();
					try {
						stmt.execute(tableCreate);
						stmt.execute(tableIndexCreate);
						logger.info("User connection table created.");
					} finally {
						stmt.close();
					}
				}
			} finally {
				result.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Unable to create user connection table", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					
				}
			}
		}
	}

}
