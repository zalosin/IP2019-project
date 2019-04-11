package com.newsbag.server.util;

/**
 * Enum for defining HTTP status codes
 * 
 * @author adrianmihaichesnoiu
 *
 */
public enum HttpStatusCode
{
	BAD_REQUEST(401),
	NOT_FOUND(404);

	private int statusCode;

	private HttpStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}
	
	public int getStatusCode()
	{
		return this.statusCode;
	}
}
