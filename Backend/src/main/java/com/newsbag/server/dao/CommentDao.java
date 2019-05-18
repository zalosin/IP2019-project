package com.newsbag.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.core.DatabaseConnector;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.model.CommentModel;
import com.newsbag.server.util.DatabaseConnectorSource;

public class CommentDao
{
	private final MainFramework mainFramework;
	private final DatabaseConnector dbConnector;

	public CommentDao() throws Exception
	{
		this.mainFramework = MainFramework.getInstance();
		this.dbConnector = mainFramework.getConnector(DatabaseConnectorSource.CORE);
	}
	
	public List<CommentModel> getAllComments() throws SQLException
	{
		final ResultSet resultSet = dbConnector.executeQuery("SELECT id, userId, articleId, text FROM comments;");
		return convertResultSetToCommentModels(resultSet);
	}
	
	public void createComment(CommentModel comment) throws SQLException
	{
		final String sql = "INSERT INTO comments (userId, articleId, text) VALUES (?,?,?)";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setInt(1, comment.getUserId());
		preparedStatement.setInt(2, comment.getArticleId());
		preparedStatement.setString(3, comment.getText());
		
		preparedStatement.execute();
		
	}

	public void updateComment(CommentModel comment) throws SQLException
	{
		final String sql = "UPDATE comments SET text=? WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setString(1, comment.getText());
		preparedStatement.setInt(2, comment.getId());
		
		preparedStatement.executeUpdate();
	}

	public void deleteComment(int commentId) throws SQLException
	{
		final String sql = "DELETE FROM comments WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setInt(1, commentId);
		preparedStatement.execute();
		
	}
	
	private List<CommentModel> convertResultSetToCommentModels(final ResultSet resultSet) throws SQLException
	{
		final List<CommentModel> comments = new ArrayList<CommentModel>();

		if(resultSet != null)
		{
			while (resultSet.next())
			{
				int id = resultSet.getInt("id");
				int userId = resultSet.getInt("userId");
				int articleId = resultSet.getInt("articleId");
				String text = resultSet.getString("text");
	
				final CommentModel comment = new CommentModel(id, userId, articleId, text);
				comments.add(comment);
			}
		}

		return comments;
	}
}
