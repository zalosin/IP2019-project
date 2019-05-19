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

import com.newsbag.server.cache.RatingCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.RatingDao;
import com.newsbag.server.model.RatingModel;
import com.newsbag.server.util.HttpStatusCode;

@Path("/rating")
public class RatingHandler
{
	private final MainFramework mainFramework;
	private final RatingCache ratingCache;
	private final RatingDao ratingDao;

	public RatingHandler()
	{
		this.mainFramework = MainFramework.getInstance();
		this.ratingCache = mainFramework.getRatingCache();
		this.ratingDao = mainFramework.getRatingDao();
	}
	
	/**
	 * Constructor using fields
	 * 
	 * @param mainFramework
	 * @param ratingCache
	 * @param ratingDao
	 */
	public RatingHandler(MainFramework mainFramework, RatingCache ratingCache, RatingDao ratingDao)
	{
		this.mainFramework = mainFramework;
		this.ratingCache = ratingCache;
		this.ratingDao = ratingDao;
	}

	@GET
	@Secured
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RatingModel> getAllRatings()
	{
		return ratingCache.getAllCachedRatings();
	}

	@GET
	@Secured
	@Path("/{ratingId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRatingById(@PathParam("ratingId") final int ratingId)
	{
		final RatingModel ratingModel = ratingCache.getRatingById(ratingId);
		
		if(ratingModel == null)
		{
			return Response.status(HttpStatusCode.NOT_FOUND.getStatusCode()).build();
		}
		
		return Response.ok(ratingCache.getRatingById(ratingId)).build();
	}
	
	@GET
	@Secured
	@Path("/byUser/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RatingModel> getRatingsByUserId(@PathParam("userId") final int userId)
	{
		return ratingCache.getRatingsByUserId(userId);
	}
	
	@GET
	@Secured
	@Path("/byArticle/{articleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RatingModel> getRatingsByArticleId(@PathParam("articleId") final int articleId)
	{
		return ratingCache.getRatingsByArticleId(articleId);
	}
	
	@GET
	@Secured
	@Path("/byUserIdAndArticleId/{userId}/{articleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RatingModel> getRatingsByUserIdAndArticleId(@PathParam("userId") final int userId, @PathParam("articleId") final int articleId)
	{
		return ratingCache.getRatingsByUserIdAndArticleId(userId, articleId);
	}

	@POST
	@Secured
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRating(final RatingModel rating) throws Exception
	{
		if(!isValid(rating))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		ratingDao.createRating(rating);
		ratingCache.reloadCache();
		
		return Response.ok().build();
	}

	@PUT
	@Secured
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateRating(final RatingModel rating) throws Exception
	{
		if(!isValid(rating))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		ratingDao.updateRating(rating);
		ratingCache.reloadCache();
		
		return Response.ok().build();
	}

	@DELETE
	@Secured
	@Path("/{ratingId}")
	public Response deteleRating(@PathParam("ratingId") final int ratingId) throws Exception
	{
		ratingDao.deleteRating(ratingId);
		ratingCache.reloadCache();
		
		return Response.ok().build();
	}

	private boolean isValid(final RatingModel ratingModel)
	{
		if (ratingModel == null || ratingModel.getValue() < 1 || ratingModel.getValue() > 5)
		{
			return false;
		}
		return true;
	}
}
