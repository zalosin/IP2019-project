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

import com.newsbag.server.cache.ArticleCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.ArticleDao;
import com.newsbag.server.handlers.pub.ArticleHandler;
import com.newsbag.server.model.ArticleModel;

/**
 * Unit test for Article Handler
 * 
 * @author adrianmihaichesnoiu
 *
 */
@RunWith(PowerMockRunner.class)
public class ArticleHandlerTest
{
	@Mock
	private ArticleDao articleDao;

	@Mock
	private ArticleCache articleCache;

	@Mock
	private MainFramework mainFramework;

	private ArticleHandler articleHandler;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		articleHandler = new ArticleHandler(mainFramework, articleCache, articleDao);
		
		Mockito.when(articleCache.getAllCachedArticles()).thenReturn(get3DummyArticles());
		Mockito.when(articleCache.getArticleById(Mockito.anyInt())).thenReturn(get3DummyArticles().get(0));
	}

	@Test
	public void testGetAllArticles()
	{
		final List<ArticleModel> returnedArticles = articleHandler.getAllArticles();

		assertEquals(returnedArticles.size(), get3DummyArticles().size());

		int i = 0;
		for (ArticleModel article : returnedArticles)
		{
			assertEquals(article, get3DummyArticles().get(i++));
		}
	}

	@Test
	public void testGetArticleById()
	{
		final ArticleModel returnedArticle = (ArticleModel) articleHandler.getArticleById(0).getEntity();
		
		assertEquals(get3DummyArticles().get(0), returnedArticle);
	}

	private List<ArticleModel> get3DummyArticles()
	{
		List<ArticleModel> articles = new ArrayList<ArticleModel>();

		articles.add(new ArticleModel(0, "title1", "body1", 1, 2, 3));
		articles.add(new ArticleModel(1, "title2", "body2", 11, 22, 33));
		articles.add(new ArticleModel(2, "title2", "body3", 111, 222, 333));

		return articles;
	}
}
