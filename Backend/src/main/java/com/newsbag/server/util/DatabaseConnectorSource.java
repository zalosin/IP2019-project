package com.newsbag.server.util;

/**
 * Enum for Database Connector sources
 * 
 * @author adrianmihaichesnoiu
 *
 */
public enum DatabaseConnectorSource
{
	CORE("newsbag_core"),
	USER1("newsbag_user1"),
	USER2("newsbag_user2");

	private String schemaName;

	private DatabaseConnectorSource(String schemaName)
	{
		this.schemaName = schemaName;
	}
	
	/**
	 * Returns the schema name
	 * 
	 * @return String
	 */
	public String getSchemaName()
	{
		return this.schemaName;
	}
}
