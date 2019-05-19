package com.newsbag.test.handlers.pub;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.newsbag.server.cache.CommentCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.CommentDao;
import com.newsbag.server.handlers.pub.CommentHandlers;

/**
 * Unit test for Comment Handler
 * 
 * @author adrianmihaichesnoiu
 *
 */
@RunWith(PowerMockRunner.class)
public class CommentHandlerTest
{
	@Mock
	private MainFramework mainFramework;
	
	@Mock
	private CommentCache commentCache;
	
	@Mock
	private CommentDao commentDao;
	
	private CommentHandlers commentHandler;
	
	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);

		commentHandler = new CommentHandlers(mainFramework, commentCache, commentDao);
	}
	
	@Test
	public void testGetAllCategories()
	{
		
	}
}
