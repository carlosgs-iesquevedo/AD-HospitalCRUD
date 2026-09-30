package es.iesquevedo.dao.utils;


import es.iesquevedo.common.Configuration;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBConnection {

	private static final Logger log = LoggerFactory.getLogger(DBConnection.class);
	private final Configuration config;

	/**
	 * Opens Database connection
	 */
	@Inject
	public DBConnection(Configuration config) {
		this.config = config;
	}

	public Connection getConnection() throws SQLException {

		Connection conn = DriverManager.getConnection(config.getProperty("urlDB"),
						config.getProperty("user_name"),
						config.getProperty("password"));
		log.info("Conectado a la BD");
		return conn;
	}


}
