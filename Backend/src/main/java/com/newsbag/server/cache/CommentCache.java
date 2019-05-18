package com.newsbag.server.cache;

import java.util.ArrayList;
import java.util.List;

import com.newsbag.server.dao.CommentDao;
import com.newsbag.server.model.CommentModel;

public class CommentCache extends AbstractCache
{
	private List<CommentModel> cachedComments = new ArrayList<CommentModel>();
	private final CommentDao commentDao;
	
	public CommentCache(final CommentDao commentDao)
	{
		this.commentDao = commentDao;
	}

	@Override
	public synchronized void reloadCache() throws Exception
	{
		cachedComments.clear();
		cachedComments = commentDao.getAllComments();
	}
	
	public synchronized List<CommentModel> getAllCachedComments()
	{
		return cachedComments;
	}
	
	public synchronized CommentModel getCommentById(final int commentId)
	{
		for(CommentModel commentModel : cachedComments)
		{
			if(commentId == commentModel.getId())
			{
				return commentModel;
			}
		}
		
		return null;
	}
	
	public synchronized List<CommentModel> getCommentsByUserId(final int userId)
	{
		final List<CommentModel> comments = new ArrayList<CommentModel>();
		for(CommentModel commentModel : cachedComments)
		{
			if(userId == commentModel.getUserId())
			{
				comments.add(commentModel);
			}
		}
		
		return comments;
	}
	
	public synchronized List<CommentModel> getCommentsByArticleId(final int articleId)
	{
		final List<CommentModel> comments = new ArrayList<CommentModel>();
		for(CommentModel commentModel : cachedComments)
		{
			if(articleId == commentModel.getArticleId())
			{
				comments.add(commentModel);
			}
		}
		
		return comments;
	}
	
	public synchronized List<CommentModel> getCommentsByUserIdAndArticleId(final int userId, final int articleId)
	{
		final List<CommentModel> comments = new ArrayList<CommentModel>();
		for(CommentModel commentModel : cachedComments)
		{
			if(userId == commentModel.getUserId() && articleId == commentModel.getArticleId())
			{
				comments.add(commentModel);
			}
		}
		
		return comments;
	}
}
