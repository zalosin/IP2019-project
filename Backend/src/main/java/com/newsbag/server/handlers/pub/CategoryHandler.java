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

import com.newsbag.server.cache.CategoryCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.CategoryDao;
import com.newsbag.server.model.CategoryModel;
import com.newsbag.server.util.HttpStatusCode;

@Path("/category")
public class CategoryHandler
{
	private final MainFramework mainFramework;
	private final CategoryCache categoryCache;
	private final CategoryDao categoryDao;

	public CategoryHandler()
	{
		this.mainFramework = MainFramework.getInstance();
		this.categoryCache = mainFramework.getCategoryCache();
		this.categoryDao = mainFramework.getCategoryDao();
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CategoryModel> getAllCategories()
	{
		return categoryCache.getAllCachedCategories();
	}

	@GET
	@Path("/{categoryId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategoryById(@PathParam("categoryId") final int categoryId)
	{
		final CategoryModel categoryModel = categoryCache.getCategoryById(categoryId);
		
		if(categoryModel == null)
		{
			return Response.status(HttpStatusCode.NOT_FOUND.getStatusCode()).build();
		}
		
		return Response.ok(categoryCache.getCategoryById(categoryId)).build();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCategory(final CategoryModel category) throws Exception
	{
		if(!isValid(category))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		categoryDao.createCategory(category);
		categoryCache.reloadCache();
		
		return Response.ok().build();
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCategory(final CategoryModel category) throws Exception
	{
		if(!isValid(category))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		categoryDao.updateCategory(category);
		categoryCache.reloadCache();
		
		return Response.ok().build();
	}

	@DELETE
	@Path("/{categoryId}")
	public Response deteleCategory(@PathParam("categoryId") final int categoryId) throws Exception
	{
		categoryDao.deleteCategory(categoryId);
		categoryCache.reloadCache();
		
		return Response.ok().build();
	}

	private boolean isValid(final CategoryModel categoryModel)
	{
		if (categoryModel == null || categoryModel.getTitle() == null)
		{
			return false;
		}
		return true;
	}
}
