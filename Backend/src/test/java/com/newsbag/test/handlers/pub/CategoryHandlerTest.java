package com.newsbag.test.handlers.pub;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.newsbag.server.cache.CategoryCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.CategoryDao;
import com.newsbag.server.handlers.pub.CategoryHandler;

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
		
	}
	
	@Test
	public void testGetAllCategories()
	{
		
	}
}
