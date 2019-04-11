package com.newsbag.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.core.DatabaseConnector;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.model.ArticleModel;
import com.newsbag.server.util.DatabaseConnectorSource;

/**
 * Dao class used for 'articles' core table
 * 
 * @author adrianmihaichesnoiu
 *
 */
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
	 * @throws SQLException 
	 */
	public void createArticle(ArticleModel article) throws SQLException
	{
		final String sql = "INSERT INTO articles (title, body, createTime, authorId) VALUES (?,?,?,?)";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setString(1, article.getTitle());
		preparedStatement.setString(2, article.getBody());
		preparedStatement.setInt(3, Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
		preparedStatement.setInt(4, article.getAuthorId());
		
		preparedStatement.execute();
		
	}

	/**
	 * Updates article
	 * 
	 * @param article
	 * @throws SQLException 
	 */
	public void updateArticle(ArticleModel article) throws SQLException
	{
		final String sql = "UPDATE articles SET title=?, body=?, createTime=?, authorId=? WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setString(1, article.getTitle());
		preparedStatement.setString(2, article.getBody());
		preparedStatement.setInt(3, Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
		preparedStatement.setInt(4, article.getAuthorId());
		preparedStatement.setInt(5, article.getId());
		
		preparedStatement.executeUpdate();
	}

	/**
	 * Deletes article by articleId
	 * 
	 * @param articleId
	 * @throws SQLException 
	 */
	public void deleteArticle(int articleId) throws SQLException
	{
		final String sql = "DELETE FROM articles WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setInt(1, articleId);
		preparedStatement.execute();
		
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

		if(resultSet != null)
		{
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
		}

		return articles;
	}
}
