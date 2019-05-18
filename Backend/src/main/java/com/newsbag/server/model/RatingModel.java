package com.newsbag.server.model;

public class RatingModel
{
	int id;
	int userId;
	int articleId;
	int value;
	
	public RatingModel()
	{
		super();
	}
	
	public RatingModel(int id, int userId, int articleId, int value)
	{
		super();
		this.id = id;
		this.userId = userId;
		this.articleId = articleId;
		this.value = value;
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	
	public int getArticleId()
	{
		return articleId;
	}

	public void setArticleId(int articleId)
	{
		this.articleId = articleId;
	}
	
	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RatingModel other = (RatingModel) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
