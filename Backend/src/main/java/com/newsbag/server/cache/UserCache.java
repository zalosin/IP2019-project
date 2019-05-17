package com.newsbag.server.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newsbag.server.dao.UserDao;
import com.newsbag.server.model.UserModel;

public class UserCache extends AbstractCache
{
	private List<UserModel> cachedUsers = new ArrayList<UserModel>();
	private final UserDao userDao;
	private Map<String, String> tokens = new HashMap<String, String>();
	
	public UserCache(final UserDao userDao)
	{
		this.userDao = userDao;
	}

	@Override
	public synchronized void reloadCache() throws Exception
	{
		cachedUsers.clear();
		cachedUsers = userDao.getAllUsers();
	}
	
	public synchronized List<UserModel> getAllCachedUsers()
	{
		return cachedUsers;
	}
	
	public synchronized UserModel getUserById(final int userId)
	{
		for(UserModel userModel : cachedUsers)
		{
			if(userId == userModel.getId())
			{
				return userModel;
			}
		}
		
		return null;
	}
	
	public synchronized UserModel getUserByUsername(final String username)
	{
		for(UserModel userModel : cachedUsers)
		{
			if(username.equals(userModel.getUsername()))
			{
				return userModel;
			}
		}
		
		return null;
	}
	
	public synchronized boolean checkUsernameAndPassword(String username, String password)
	{
		for (UserModel user : cachedUsers)
		{
			if (user.getUsername().equals(username) && user.getPassword().equals(password))
			{
				return true;
			}
		}

		return false;
	}
	
	public synchronized void storeToken(String token, String username)
	{
		tokens.put(token, username);
	}
	
	public synchronized boolean validateToken(String token)
	{
		return tokens.containsKey(token);
	}
	
	public synchronized String getUsernameFromToken(String token)
	{
		if (tokens.containsKey(token))
		{
			return tokens.get(token);
		}

		return null;
	}
}
