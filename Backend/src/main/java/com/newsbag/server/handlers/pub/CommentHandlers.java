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

import com.newsbag.server.cache.CommentCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.CommentDao;
import com.newsbag.server.model.CommentModel;
import com.newsbag.server.util.HttpStatusCode;

@Path("/comment")
public class CommentHandlers
{
	private final MainFramework mainFramework;
	private final CommentCache commentCache;
	private final CommentDao commentDao;

	public CommentHandlers()
	{
		this.mainFramework = MainFramework.getInstance();
		this.commentCache = mainFramework.getCommentCache();
		this.commentDao = mainFramework.getCommentDao();
	}
	
	
	/**
	 * Constructor using fields
	 * 
	 * @param mainFramework
	 * @param commentCache
	 * @param commentDao
	 */
	public CommentHandlers(MainFramework mainFramework, CommentCache commentCache, CommentDao commentDao)
	{
		this.mainFramework = mainFramework;
		this.commentCache = commentCache;
		this.commentDao = commentDao;
	}

	@GET
	@Secured
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommentModel> getAllComments()
	{
		return commentCache.getAllCachedComments();
	}

	@GET
	@Secured
	@Path("/{commentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentById(@PathParam("commentId") final int commentId)
	{
		final CommentModel commentModel = commentCache.getCommentById(commentId);
		
		if(commentModel == null)
		{
			return Response.status(HttpStatusCode.NOT_FOUND.getStatusCode()).build();
		}
		
		return Response.ok(commentCache.getCommentById(commentId)).build();
	}
	
	@GET
	@Secured
	@Path("/byUser/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommentModel> getCommentsByUserId(@PathParam("userId") final int userId)
	{
		return commentCache.getCommentsByUserId(userId);
	}
	
	@GET
	@Secured
	@Path("/byArticle/{articleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommentModel> getCommentsByArticleId(@PathParam("articleId") final int articleId)
	{
		return commentCache.getCommentsByArticleId(articleId);
	}
	
	@GET
	@Secured
	@Path("/byUserIdAndArticleId/{userId}/{articleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommentModel> getCommentsByUserIdAndArticleId(@PathParam("userId") final int userId, @PathParam("articleId") final int articleId)
	{
		return commentCache.getCommentsByUserIdAndArticleId(userId, articleId);
	}

	@POST
	@Secured
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createComment(final CommentModel comment) throws Exception
	{
		if(!isValid(comment))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		commentDao.createComment(comment);
		commentCache.reloadCache();
		
		return Response.ok().build();
	}

	@PUT
	@Secured
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateComment(final CommentModel comment) throws Exception
	{
		if(!isValid(comment))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		commentDao.updateComment(comment);
		commentCache.reloadCache();
		
		return Response.ok().build();
	}

	@DELETE
	@Secured
	@Path("/{commentId}")
	public Response deteleComment(@PathParam("commentId") final int commentId) throws Exception
	{
		commentDao.deleteComment(commentId);
		commentCache.reloadCache();
		
		return Response.ok().build();
	}

	private boolean isValid(final CommentModel commentModel)
	{
		if (commentModel == null || commentModel.getText() == null)
		{
			return false;
		}
		return true;
	}
}
