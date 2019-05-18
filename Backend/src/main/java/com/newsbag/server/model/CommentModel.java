package com.newsbag.server.model;

public class CommentModel
{
	int id;
	int userId;
	int articleId;
	String text;
	
	public CommentModel()
	{
		super();
	}
	
	public CommentModel(int id, int userId, int articleId, String text)
	{
		super();
		this.id = id;
		this.userId = userId;
		this.articleId = articleId;
		this.text = text;
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
	
	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
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
		CommentModel other = (CommentModel) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
