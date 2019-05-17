package com.newsbag.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.core.DatabaseConnector;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.model.UserModel;
import com.newsbag.server.util.DatabaseConnectorSource;

public class UserDao
{
	private final MainFramework mainFramework;
	private final DatabaseConnector dbConnector;
	
	public UserDao() throws Exception
	{
		this.mainFramework = MainFramework.getInstance();
		this.dbConnector = mainFramework.getConnector(DatabaseConnectorSource.CORE);
	}
	
	public List<UserModel> getAllUsers() throws SQLException
	{
		final ResultSet resultSet = dbConnector.executeQuery("SELECT id, username, password FROM users;");
		return convertResultSetToUserModels(resultSet);
	}
	
	public void createUser(UserModel user) throws SQLException
	{
		final String sql = "INSERT INTO users (username, password) VALUES (?,?)";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getPassword());
		
		preparedStatement.execute();
		
	}

	public void updateUser(UserModel user) throws SQLException
	{
		final String sql = "UPDATE users SET username=?, password=? WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getPassword());
		preparedStatement.setInt(3, user.getId());
		
		preparedStatement.executeUpdate();
	}

	public void deleteUser(int userId) throws SQLException
	{
		final String sql = "DELETE FROM users WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setInt(1, userId);
		preparedStatement.execute();
		
	}
	
	private List<UserModel> convertResultSetToUserModels(final ResultSet resultSet) throws SQLException
	{
		final List<UserModel> users = new ArrayList<UserModel>();

		if(resultSet != null)
		{
			while (resultSet.next())
			{
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
	
				final UserModel user = new UserModel(id, username, password);
				users.add(user);
			}
		}

		return users;
	}
}
