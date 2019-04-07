package com.newsbag.server.util;

/**
 * Enum for Database Connector sources
 * 
 * @author adrianmihaichesnoiu
 *
 */
public enum DatabaseConnectorSource
{
	CORE("newsbag_core");

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
