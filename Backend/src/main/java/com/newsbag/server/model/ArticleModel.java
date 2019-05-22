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
	int categoryId;
	String imageLink;

	public ArticleModel()
	{
		super();
	}

	public ArticleModel(int id, String title, String body, int createTime, int authorId, int categoryId, String imageLink)
	{
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.createTime = createTime;
		this.authorId = authorId;
		this.categoryId = categoryId;
		this.imageLink = imageLink;
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
	
	public int getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	

	public String getImageLink()
	{
		return imageLink;
	}

	public void setImageLink(String imageLink)
	{
		this.imageLink = imageLink;
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
