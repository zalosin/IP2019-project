package com.newsbag.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.core.DatabaseConnector;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.model.CategoryModel;
import com.newsbag.server.util.DatabaseConnectorSource;

public class CategoryDao
{
	private final MainFramework mainFramework;
	private final DatabaseConnector dbConnector;

	public CategoryDao() throws Exception
	{
		this.mainFramework = MainFramework.getInstance();
		this.dbConnector = mainFramework.getConnector(DatabaseConnectorSource.CORE);
	}
	
	public List<CategoryModel> getAllCategories() throws SQLException
	{
		final ResultSet resultSet = dbConnector.executeQuery("SELECT id, title FROM categories;");
		return convertResultSetToCategoryModels(resultSet);
	}
	
	public void createCategory(CategoryModel category) throws SQLException
	{
		final String sql = "INSERT INTO categories (title) VALUES (?)";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setString(1, category.getTitle());
		
		preparedStatement.execute();
		
	}

	public void updateCategory(CategoryModel category) throws SQLException
	{
		final String sql = "UPDATE categories SET title=? WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setString(1, category.getTitle());
		preparedStatement.setInt(2, category.getId());
		
		preparedStatement.executeUpdate();
	}

	public void deleteCategory(int categoryId) throws SQLException
	{
		final String sql = "DELETE FROM categories WHERE id = ?";
		final PreparedStatement preparedStatement = dbConnector.getPreparedStatement(sql);
		
		preparedStatement.setInt(1, categoryId);
		preparedStatement.execute();
		
	}
	
	private List<CategoryModel> convertResultSetToCategoryModels(final ResultSet resultSet) throws SQLException
	{
		final List<CategoryModel> categories = new ArrayList<CategoryModel>();

		if(resultSet != null)
		{
			while (resultSet.next())
			{
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
	
				final CategoryModel category = new CategoryModel(id, title);
				categories.add(category);
			}
		}

		return categories;
	}
}
