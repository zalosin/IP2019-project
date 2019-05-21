package com.newsbag.test.handlers.pub;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.newsbag.server.cache.CategoryCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.CategoryDao;
import com.newsbag.server.handlers.pub.CategoryHandler;
import com.newsbag.server.model.CategoryModel;

/**
 * Unit test for Category Handler
 * 
 * @author adrianmihaichesnoiu
 *
 */
@RunWith(PowerMockRunner.class)
public class CategoryHandlerTest
{
	@Mock
	private MainFramework mainFramework;

	@Mock
	private CategoryCache categoryCache;

	@Mock
	private CategoryDao categoryDao;

	private CategoryHandler categoryHandler;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);

		categoryHandler = new CategoryHandler(mainFramework, categoryCache, categoryDao);

		Mockito.when(categoryCache.getAllCachedCategories()).thenReturn(get3DummyCategories());
		Mockito.when(categoryCache.getCategoryById(Mockito.anyInt())).thenReturn(get3DummyCategories().get(0));
	}

	@Test
	public void testGetAllCategories()
	{
		final List<CategoryModel> returnedCategories = categoryHandler.getAllCategories();

		assertEquals(returnedCategories.size(), get3DummyCategories().size());

		int i = 0;
		for (CategoryModel category : returnedCategories)
		{
			assertEquals(category, get3DummyCategories().get(i++));
		}
	}

	@Test
	public void testGetCategoryById()
	{
		final CategoryModel returnedCategory = (CategoryModel) categoryHandler
				.getCategoryById(get3DummyCategories().get(0).getId()).getEntity();
		assertEquals(returnedCategory, get3DummyCategories().get(0));
	}

	private List<CategoryModel> get3DummyCategories()
	{
		List<CategoryModel> categories = new ArrayList<CategoryModel>();

		categories.add(new CategoryModel(1, "title1"));
		categories.add(new CategoryModel(2, "title2"));
		categories.add(new CategoryModel(3, "title3"));

		return categories;
	}
}
