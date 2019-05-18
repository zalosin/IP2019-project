package com.newsbag.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.core.DatabaseConnector;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.model.RatingModel;
import com.newsbag.server.util.DatabaseConnectorSource;

public class RatingDao
{
	private final MainFramework mainFramework;
	private final DatabaseConnector dbConnector;

	public RatingDao() throws Exception
	{
		this.mainFramework = MainFramework.getInstance();
		this.dbConnector = mainFramework.getConnector(DatabaseConnectorSource.CORE);
	}
	
	public List<RatingModel> getAllRatings() throws SQLException
	{
		final ResultSet resultSet = dbConnector.executeQuery("SELECT id, userId, articleId, value FROM ratings;");
		return convertResultSetToRatingModels(resultSet);
	}
	
	public void createRating(RatingModel rating) throws SQLException
	{
		final String sql = "INSERT INTO ratings (userId, articleId, value) VALUES (?,?,?)";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setInt(1, rating.getUserId());
		preparedStatement.setInt(2, rating.getArticleId());
		preparedStatement.setInt(3, rating.getValue());
		
		preparedStatement.execute();
	}

	public void updateRating(RatingModel rating) throws SQLException
	{
		final String sql = "UPDATE ratings SET value=? WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setInt(1, rating.getValue());
		preparedStatement.setInt(2, rating.getId());
		
		preparedStatement.executeUpdate();
	}

	public void deleteRating(int ratingId) throws SQLException
	{
		final String sql = "DELETE FROM ratings WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setInt(1, ratingId);
		preparedStatement.execute();
	}
	
	private List<RatingModel> convertResultSetToRatingModels(final ResultSet resultSet) throws SQLException
	{
		final List<RatingModel> ratings = new ArrayList<RatingModel>();

		if(resultSet != null)
		{
			while (resultSet.next())
			{
				int id = resultSet.getInt("id");
				int userId = resultSet.getInt("userId");
				int articleId = resultSet.getInt("articleId");
				int value = resultSet.getInt("value");
	
				final RatingModel rating = new RatingModel(id, userId, articleId, value);
				ratings.add(rating);
			}
		}

		return ratings;
	}
}
