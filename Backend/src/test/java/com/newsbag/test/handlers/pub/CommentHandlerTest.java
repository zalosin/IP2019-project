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

import com.newsbag.server.cache.CommentCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.CommentDao;
import com.newsbag.server.handlers.pub.CommentHandlers;
import com.newsbag.server.model.CommentModel;

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
		
		Mockito.when(commentCache.getAllCachedComments()).thenReturn(get3DummyComments());
		Mockito.when(commentCache.getCommentById(Mockito.anyInt())).thenReturn(get3DummyComments().get(0));
	}
	
	@Test
	public void testGetAllComments()
	{
		final List<CommentModel> returnedComments = commentHandler.getAllComments();

		assertEquals(returnedComments.size(), get3DummyComments().size());

		int i = 0;
		for (CommentModel comment : returnedComments)
		{
			assertEquals(comment, get3DummyComments().get(i++));
		}
	}
	
	@Test
	public void testGetCommentById()
	{
		final CommentModel returnedComment= (CommentModel) commentHandler
				.getCommentById(get3DummyComments().get(0).getId()).getEntity();
		assertEquals(returnedComment, get3DummyComments().get(0));
	}
	
	private List<CommentModel> get3DummyComments()
	{
		List<CommentModel> comments = new ArrayList<CommentModel>();

		comments.add(new CommentModel(0, 0, 0, "text"));
		comments.add(new CommentModel(1, 1, 1, "text1"));
		comments.add(new CommentModel(1, 1, 1, "text2"));

		return comments;
	}
}
