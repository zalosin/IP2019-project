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
	public synchronized List<ArticleModel> getAllCachedArticles()
	{
		return cachedArticles;
	}

	/**
	 * Returns article by id
	 * 
	 * @param articleId
	 * @return ArticleModel | null in case there is no such article
	 */
	public synchronized ArticleModel getArticleById(final int articleId)
	{
		for (ArticleModel articleModel : cachedArticles)
		{
			if (articleId == articleModel.getId())
			{
				return articleModel;
			}
		}

		return null;
	}

	/**
	 * Returns all the articles given a list of ids
	 * 
	 * @param articleIds
	 * @return List<ArticleModel>
	 */
	public synchronized List<ArticleModel> getArticleByIds(final List<Integer> articleIds)
	{
		final List<ArticleModel> articles = new ArrayList<ArticleModel>();

		for (ArticleModel article : articles)
		{
			if (articleIds.contains(article.getId()))
			{
				articles.add(article);
			}
		}

		return articles;
	}

}
