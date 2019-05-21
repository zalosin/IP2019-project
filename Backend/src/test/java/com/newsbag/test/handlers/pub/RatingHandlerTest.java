package com.newsbag.test.handlers.pub;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.newsbag.server.cache.RatingCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.RatingDao;
import com.newsbag.server.dao.RecombeeDao;
import com.newsbag.server.handlers.pub.RatingHandler;

/**
 * Unit test for Rating Handler
 * 
 * @author adrianmihaichesnoiu
 *
 */
@RunWith(PowerMockRunner.class)
public class RatingHandlerTest
{
	@Mock
	private MainFramework mainFramework;
	
	@Mock
	private RatingCache ratingCache;
	
	@Mock
	private RatingDao ratingDao;
	
	@Mock
	private RecombeeDao recombeeDao;
	
	
	private RatingHandler ratingHandler;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);

		ratingHandler = new RatingHandler(mainFramework, ratingCache, ratingDao, recombeeDao);
	}
	
	@Test
	public void testGetAllCategories()
	{
		
	}
}
