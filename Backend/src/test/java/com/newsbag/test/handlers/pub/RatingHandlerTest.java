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

import com.newsbag.server.cache.RatingCache;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.dao.RatingDao;
import com.newsbag.server.dao.RecombeeDao;
import com.newsbag.server.handlers.pub.RatingHandler;
import com.newsbag.server.model.RatingModel;

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

		Mockito.when(ratingCache.getAllCachedRatings()).thenReturn(get3DummyRatings());
		Mockito.when(ratingCache.getRatingById(Mockito.anyInt())).thenReturn(get3DummyRatings().get(0));
	}

	@Test
	public void testGetAllRatings()
	{
		final List<RatingModel> returnedRatings = ratingHandler.getAllRatings();

		assertEquals(returnedRatings.size(), get3DummyRatings().size());

		int i = 0;
		for (RatingModel rating : returnedRatings)
		{
			assertEquals(rating, get3DummyRatings().get(i++));
		}
	}

	@Test
	public void testGetRatingById()
	{
		final RatingModel returnedRating = (RatingModel) ratingHandler.getRatingById(0).getEntity();

		assertEquals(get3DummyRatings().get(0), returnedRating);
	}

	private List<RatingModel> get3DummyRatings()
	{
		List<RatingModel> ratings = new ArrayList<RatingModel>();

		ratings.add(new RatingModel(0, 0, 0, 0));
		ratings.add(new RatingModel(1, 1, 1, 1));
		ratings.add(new RatingModel(1, 1, 1, 1));

		return ratings;
	}

}
