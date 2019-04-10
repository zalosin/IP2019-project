package com.newsbag.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.core.DatabaseConnector;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.model.ArticleModel;
import com.newsbag.server.util.DatabaseConnectorSource;

public class ArticleDao
{
	private final MainFramework mainFramework;
	private final DatabaseConnector dbConnector;

	public ArticleDao() throws Exception
	{
		this.mainFramework = MainFramework.getInstance();
		this.dbConnector = mainFramework.getConnector(DatabaseConnectorSource.CORE);
	}

	/**
	 * Returns all the articles from db
	 * 
	 * @return List<ArticleModel>
	 * @throws SQLException
	 */
	public List<ArticleModel> getAllArticles() throws SQLException
	{
		final ResultSet resultSet = dbConnector
				.executeQuery("SELECT id, title, body, createTime, authorId FROM articles;");

		return convertResultSetToArticleModels(resultSet);
	}

	/**
	 * Creates article
	 * 
	 * @param article
	 */
	public void createArticle(ArticleModel article)
	{
		// TODO
	}

	/**
	 * Updates article
	 * 
	 * @param article
	 */
	public void updateArticle(ArticleModel article)
	{
		// TODO
	}

	/**
	 * Deletes article by articleId
	 * 
	 * @param articleId
	 */
	public void deleteArticle(int articleId)
	{
		// TODO
	}

	/**
	 * Converts a results set to a list of Article Models
	 * 
	 * @param resultSet
	 * @return List<ArticleModel>
	 * @throws SQLException
	 */
	private List<ArticleModel> convertResultSetToArticleModels(final ResultSet resultSet) throws SQLException
	{
		final List<ArticleModel> articles = new ArrayList<ArticleModel>();

		while (resultSet.next())
		{
			int id = resultSet.getInt("id");
			String title = resultSet.getString("title");
			String body = resultSet.getString("body");
			int createTime = resultSet.getInt("createTime");
			int authorId = resultSet.getInt("authorId");

			final ArticleModel article = new ArticleModel(id, title, body, createTime, authorId);
			articles.add(article);
		}

		return articles;
	}
}
