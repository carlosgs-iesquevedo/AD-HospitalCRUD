package es.iesquevedo.dao.utils;


import es.iesquevedo.common.Configuration;
import jakarta.inject.Inject;

import java.sql.*;


public class DBConnection {

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
		System.out.println("Connected to DB");
		return conn;
	}


}
