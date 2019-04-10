package com.newsbag.server.cache;

import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.dao.ArticleDao;
import com.newsbag.server.model.ArticleModel;

/**
 * Cached used for persisting existing articles in memory
 * 
 * @author adrianmihaichesnoiu
 *
 */
public class ArticleCache extends AbstractCache
{
	private List<ArticleModel> cachedArticles = new ArrayList<ArticleModel>();

	private final ArticleDao articleDao;
	
	/**
	 * Constructor
	 *
	 * @param articleDao
	 */
	public ArticleCache(final ArticleDao articleDao)
	{
		this.articleDao = articleDao;
	}

	/**
	 * Reload cahce
	 */
	@Override
	public synchronized void reloadCache() throws Exception
	{
		cachedArticles.clear();
		cachedArticles = articleDao.getAllArticles();
	}
	
	/**
	 * Returns all the cached articles
	 * 
	 * @return List<ArticleModel>
	 */
	public List<ArticleModel> getAllCachedArticles()
	{
		return cachedArticles;
	}
	
	
}
