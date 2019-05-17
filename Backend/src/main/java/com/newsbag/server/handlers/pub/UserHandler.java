package com.newsbag.server.handlers.pub;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.newsbag.server.cache.UserCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.UserDao;
import com.newsbag.server.model.UserModel;
import com.newsbag.server.util.HttpStatusCode;

@Path("/user")
public class UserHandler
{
	private final MainFramework mainFramework;
	private final UserCache userCache;
	private final UserDao userDao;

	public UserHandler()
	{
		this.mainFramework = MainFramework.getInstance();
		this.userCache = mainFramework.getUserCache();
		this.userDao = mainFramework.getUserDao();
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserModel> getAllUsers()
	{
		return userCache.getAllCachedUsers();
	}

	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("userId") final int userId)
	{
		final UserModel userModel = userCache.getUserById(userId);
		
		if(userModel == null)
		{
			return Response.status(HttpStatusCode.NOT_FOUND.getStatusCode()).build();
		}
		
		return Response.ok(userCache.getUserById(userId)).build();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(final UserModel user) throws Exception
	{
		if(!isValid(user))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		userDao.createUser(user);
		userCache.reloadCache();
		
		return Response.ok().build();
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(final UserModel user) throws Exception
	{
		if(!isValid(user))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		userDao.updateUser(user);
		userCache.reloadCache();
		
		return Response.ok().build();
	}

	@DELETE
	@Path("/{userId}")
	public Response deteleUser(@PathParam("userId") final int userId) throws Exception
	{
		userDao.deleteUser(userId);
		userCache.reloadCache();
		
		return Response.ok().build();
	}

	private boolean isValid(final UserModel userModel)
	{
		if (userModel == null || userModel.getUsername() == null || userModel.getPassword() == null)
		{
			return false;
		}
		return true;
	}
}
