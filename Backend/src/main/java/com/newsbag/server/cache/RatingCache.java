package com.newsbag.server.cache;

import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.dao.RatingDao;
import com.newsbag.server.model.RatingModel;

public class RatingCache extends AbstractCache
{
	private List<RatingModel> cachedRatings = new ArrayList<RatingModel>();
	private final RatingDao ratingDao;
	
	public RatingCache(final RatingDao ratingDao)
	{
		this.ratingDao = ratingDao;
	}

	@Override
	public synchronized void reloadCache() throws Exception
	{
		cachedRatings.clear();
		cachedRatings = ratingDao.getAllRatings();
	}
	
	public synchronized List<RatingModel> getAllCachedRatings()
	{
		return cachedRatings;
	}
	
	public synchronized RatingModel getRatingById(final int ratingId)
	{
		for(RatingModel ratingModel : cachedRatings)
		{
			if(ratingId == ratingModel.getId())
			{
				return ratingModel;
			}
		}
		
		return null;
	}
	
	public synchronized List<RatingModel> getRatingsByUserId(final int userId)
	{
		final List<RatingModel> ratings = new ArrayList<RatingModel>();
		for(RatingModel ratingModel : cachedRatings)
		{
			if(userId == ratingModel.getUserId())
			{
				ratings.add(ratingModel);
			}
		}
		
		return ratings;
	}
	
	public synchronized List<RatingModel> getRatingsByArticleId(final int articleId)
	{
		final List<RatingModel> ratings = new ArrayList<RatingModel>();
		for(RatingModel ratingModel : cachedRatings)
		{
			if(articleId == ratingModel.getArticleId())
			{
				ratings.add(ratingModel);
			}
		}
		
		return ratings;
	}
	
	public synchronized List<RatingModel> getRatingsByUserIdAndArticleId(final int userId, final int articleId)
	{
		final List<RatingModel> ratings = new ArrayList<RatingModel>();
		for(RatingModel ratingModel : cachedRatings)
		{
			if(userId == ratingModel.getUserId() && articleId == ratingModel.getArticleId())
			{
				ratings.add(ratingModel);
			}
		}
		
		return ratings;
	}
}
