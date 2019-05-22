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

import com.newsbag.server.cache.ArticleCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.ArticleDao;
import com.newsbag.server.dao.RecombeeDao;
import com.newsbag.server.model.ArticleModel;
import com.newsbag.server.util.HttpStatusCode;

/**
 * Public handler used for returning articles
 * 
 * @author adrianmihaichesnoiu
 *
 */
@Path("/article")
public class ArticleHandler
{
	private final MainFramework mainFramework;
	private final ArticleCache articleCache;
	private final ArticleDao articleDao;
	private final RecombeeDao recombeeDao;

	/**
	 * Constructor
	 */
	public ArticleHandler()
	{
		this.mainFramework = MainFramework.getInstance();
		this.articleCache = mainFramework.getArticleCache();
		this.articleDao = mainFramework.getArticleDao();
		this.recombeeDao = mainFramework.getRecombeeDao();
	}

	/**
	 * Constructor using fields
	 * 
	 * @param mainFramework
	 * @param articleCache
	 * @param articleDao
	 * @param recombeeDao
	 */
	public ArticleHandler(MainFramework mainFramework, ArticleCache articleCache, ArticleDao articleDao, RecombeeDao recombeeDao)
	{
		this.mainFramework = mainFramework;
		this.articleCache = articleCache;
		this.articleDao = articleDao;
		this.recombeeDao = recombeeDao;
	}

	/**
	 * Returns all the cached articles
	 * 
	 * @return List<ArticleModel>
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArticleModel> getAllArticles()
	{
		// return from cache, not from DB
		return articleCache.getAllCachedArticles();
	}

	/**
	 * Returns article by id
	 * 
	 * @param articleId
	 * @return
	 */
	@GET
	@Path("/{articleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArticleById(@PathParam("articleId") final int articleId)
	{
		final ArticleModel articleModel = articleCache.getArticleById(articleId);
		
		if(articleModel == null)
		{
			return Response.status(HttpStatusCode.NOT_FOUND.getStatusCode()).build();
		}
		
		return Response.ok(articleCache.getArticleById(articleId)).build();
	}
	
	/**
	 * Returns recommended articles by id
	 * 
	 * @param userId
	 * @return Response
	 */
	@GET
	@Path("/reccomended/{userId}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArticleModel> getRecommendedArticles(@PathParam("userId") final int userId)
	{
		final List<Integer> recommendedArticleIds = recombeeDao.getReccomendedArticlesForUser(userId);
		
		return articleCache.getArticleByIds(recommendedArticleIds);
	}

	/**
	 * Creates article
	 * 
	 * @param article
	 * @return Response
	 * @throws Exception 
	 */
	@POST
	@Secured
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createArticle(final ArticleModel article) throws Exception
	{
		if(!isValid(article))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		// TODO move to controller
		articleDao.createArticle(article);
		articleCache.reloadCache();
		
		return Response.ok().build();
	}

	/**
	 * Updates article
	 * 
	 * @param article
	 * @return Response
	 * @throws Exception 
	 */
	@PUT
	@Secured
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateArticle(final ArticleModel article) throws Exception
	{
		if(!isValid(article))
		{
			return Response.status(HttpStatusCode.BAD_REQUEST.getStatusCode()).build();
		}
		
		// TODO move to controller
		articleDao.updateArticle(article);
		articleCache.reloadCache();
		
		return Response.ok().build();
	}

	/**
	 * Delete article
	 * 
	 * @param articleId
	 * @return Response
	 * @throws Exception 
	 */
	@DELETE
	@Secured
	@Path("/{articleId}")
	public Response deteleArticle(@PathParam("articleId") final int articleId) throws Exception
	{
		// TODO move to controller
		articleDao.deleteArticle(articleId);
		articleCache.reloadCache();
		
		return Response.ok().build();
	}
	
	/**
	 * Checks if the provided article is invalid
	 * 
	 * @param articleModel
	 * @return boolean
	 */
	private boolean isValid(final ArticleModel articleModel)
	{
		if (articleModel == null || articleModel.getTitle() == null || articleModel.getBody() == null || articleModel.getAuthorId() == 0)
		{
			return false;
		}
		return true;
	}

}
