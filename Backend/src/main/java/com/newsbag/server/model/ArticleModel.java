package com.newsbag.server.model;

/**
 * Model used for holding article information
 * 
 * @author adrianmihaichesnoiu
 *
 */
public class ArticleModel
{
	int id;
	String title;
	String body;
	int createTime;
	int authorId;

	public ArticleModel()
	{
		super();
	}

	public ArticleModel(int id, String title, String body, int createTime, int authorId)
	{
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.createTime = createTime;
		this.authorId = authorId;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public int getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(int createTime)
	{
		this.createTime = createTime;
	}

	public int getAuthorId()
	{
		return authorId;
	}

	public void setAuthorId(int authorId)
	{
		this.authorId = authorId;
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
		ArticleModel other = (ArticleModel) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
