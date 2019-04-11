package com.newsbag.server.model;

public class CategoryModel
{
	int id;
	String title;
	
	public CategoryModel()
	{
		super();
	}
	
	public CategoryModel(int id, String title)
	{
		super();
		this.id = id;
		this.title = title;
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
		CategoryModel other = (CategoryModel) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
