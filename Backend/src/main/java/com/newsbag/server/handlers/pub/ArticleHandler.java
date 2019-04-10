package com.newsbag.server.handlers.pub;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.newsbag.server.cache.ArticleCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.model.ArticleModel;

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
	
	/**
	 * Constructor
	 */
	public ArticleHandler()
	{
		this.mainFramework = MainFramework.getInstance();
		this.articleCache = mainFramework.getArticleCache();
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
	
}
