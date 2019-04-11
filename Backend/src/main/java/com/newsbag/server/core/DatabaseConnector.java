package com.newsbag.server.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;

import com.newsbag.server.util.DatabaseConnectorSource;

/**
 * Database accessor
 * 
 * @author adrianmihaichesnoiu
 *
 */
public class DatabaseConnector
{
	// The mysql jdbc driver
	private static final String JDBC_DRIVER_NAME = "com.mysql.jdbc.Driver";

	// Connection
	private Connection connection = null;

	// Connector source
	private DatabaseConnectorSource type;

	/**
	 * Constructor
	 * 
	 * @param user
	 * @param password
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public DatabaseConnector(final DatabaseConnectorSource type, final String user, final String password,
			final String dbPath, final String schema) throws SQLException, ClassNotFoundException
	{
		this.type = type;

		Class.forName(JDBC_DRIVER_NAME);

		connection = DriverManager.getConnection("jdbc:mysql://" + dbPath + "/" + schema + "?user=" + user
				+ "&serverTimezone=" + TimeZone.getDefault().getID() + "&password=" + password);
	}

	/**
	 * Executes SQL query
	 * 
	 * @param sqlQuery
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet executeQuery(final String sqlQuery) throws SQLException
	{
		final Statement statement = connection.createStatement();
		return statement.executeQuery(sqlQuery);
	}
	
	public PreparedStatement getPreparedStatement(final String sqlQuery) throws SQLException
	{
		final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
		return preparedStatement;
	}

	/**
	 * Returns the connector source
	 * 
	 * @return DatabaseConnectorSource
	 */
	public DatabaseConnectorSource getType()
	{
		return this.type;
	}
}
