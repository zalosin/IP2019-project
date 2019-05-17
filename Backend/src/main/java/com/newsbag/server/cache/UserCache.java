package com.newsbag.server.cache;

import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.dao.UserDao;
import com.newsbag.server.model.UserModel;

public class UserCache extends AbstractCache
{
	private List<UserModel> cachedUsers = new ArrayList<UserModel>();
	private final UserDao userDao;
	
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
}
