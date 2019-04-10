package com.newsbag.server.cache;

/**
 * Abstract class for defining the contract to be respected for caches
 * 
 * @author adrianmihaichesnoiu
 *
 */
public abstract class AbstractCache
{
	/**
	 * Used for reloading cache data from DB
	 * 
	 * @throws Exception
	 */
	public abstract void reloadCache() throws Exception;
}
