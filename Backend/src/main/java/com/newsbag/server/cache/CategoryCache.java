package com.newsbag.server.cache;

import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.dao.CategoryDao;
import com.newsbag.server.model.CategoryModel;

public class CategoryCache extends AbstractCache
{
	private List<CategoryModel> cachedCategories = new ArrayList<CategoryModel>();
	private final CategoryDao categoryDao;
	
	public CategoryCache(final CategoryDao categoryDao)
	{
		this.categoryDao = categoryDao;
	}

	@Override
	public synchronized void reloadCache() throws Exception
	{
		cachedCategories.clear();
		cachedCategories = categoryDao.getAllCategories();
	}
	
	public synchronized List<CategoryModel> getAllCachedCategories()
	{
		return cachedCategories;
	}
	
	public synchronized CategoryModel getCategoryById(final int categoryId)
	{
		for(CategoryModel categoryModel : cachedCategories)
		{
			if(categoryId == categoryModel.getId())
			{
				return categoryModel;
			}
		}
		
		return null;
	}
}
