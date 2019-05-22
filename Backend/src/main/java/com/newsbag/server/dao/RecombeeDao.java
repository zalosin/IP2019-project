package com.newsbag.server.dao;

import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.model.RatingModel;
import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.AddPurchase;
import com.recombee.api_client.api_requests.Batch;
import com.recombee.api_client.api_requests.RecommendItemsToUser;
import com.recombee.api_client.api_requests.Request;
import com.recombee.api_client.api_requests.UserBasedRecommendation;
import com.recombee.api_client.bindings.Recommendation;
import com.recombee.api_client.bindings.RecommendationResponse;
import com.recombee.api_client.exceptions.ApiException;

/**
 * DAO user for the communication with the Recombee service
 * 
 * @author adrianmihaichesnoiu
 *
 */
public class RecombeeDao
{
	private static final String RECOMBEE_DB_ID = "newsbag-finalprod";
	private static final String RECOMBEE_DB_PRIVATE_TOKEN = "kgZlFbpz7CRDid6hK6RMZIdUBFE97bEJH4zglRpguQ7h6Rv8eM1CmPijitIOigqH";
	private static final int NUMBER_OF_RECCOMANDATIONS_TO_RETRIEVE = 5;
	private static final int MINIMUM_RATING_TO_BE_PERSISTED = 4;

	/**
	 * Constructor
	 */
	public RecombeeDao()
	{
	}

	/**
	 * Method used for trainning the Recombee AI given a rating the user did
	 * 
	 * @param ratingModel
	 */
	public void trainRecombeeByUserRating(final RatingModel ratingModel)
	{
		if(ratingModel.getValue() < MINIMUM_RATING_TO_BE_PERSISTED)
		{
			return;
		}
		Thread recombeeThread = new Thread(() ->
		{
			RecombeeClient client = new RecombeeClient(RECOMBEE_DB_ID, RECOMBEE_DB_PRIVATE_TOKEN);
			try
			{
				final List<Request> requests = new ArrayList<>();
				AddPurchase request = new AddPurchase(String.format("user-%s", ratingModel.getUserId()),
						String.format("item-%s", ratingModel.getArticleId())).setCascadeCreate(true);
				requests.add(request);

				client.send(new Batch(requests)); // Use Batch for faster processing of larger data

			} catch (ApiException e)
			{
				e.printStackTrace();
				System.err.println("Error occured when contacting Recombee service for trainning: " + e);
			}
		});

		recombeeThread.start();
	}

	/**
	 * Returns the reccomended articles for a user
	 * 
	 * @param userId
	 * @return 5 reccomended article ids
	 */
	public List<Integer> getReccomendedArticlesForUser(int userId)
	{
		final List<Integer> articleIds = new ArrayList<Integer>();

		try
		{
			RecombeeClient client = new RecombeeClient(RECOMBEE_DB_ID, RECOMBEE_DB_PRIVATE_TOKEN);

			Recommendation[] recommendationResponse = client.send(
					new UserBasedRecommendation(String.format("user-%s", userId), NUMBER_OF_RECCOMANDATIONS_TO_RETRIEVE));

			for (Recommendation rec : recommendationResponse)
			{
				articleIds.add(Integer.valueOf(rec.getId().replaceAll("item-", "")));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Error occured when contacting Recombee service for retrieveing reccomandations: " + e);
		}

		return articleIds;
	}
}
