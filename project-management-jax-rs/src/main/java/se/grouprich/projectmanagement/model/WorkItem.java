package se.grouprich.projectmanagement.model;

import javax.persistence.Entity;

import se.grouprich.projectmanagement.status.WorkItemStatus;

@Entity
public final class WorkItem extends AbstractEntity
{
	private String title;
	private User user;
	private String description;
	private String status;
	
	protected WorkItem(){}

	public WorkItem(Long id, String controlNumber, String title, String status)
	{
		super(id, controlNumber);
		this.title = title;
		this.status = status;
	}

	public User getUser()
	{
		return user;
	}

	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(final String status)
	{
		this.status = status;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public WorkItem setUser(User user)
	{
		this.user = user;
		return this;
	}
	
	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}
		
		if (other instanceof WorkItem)
		{
			WorkItem otherWorkItem = (WorkItem) other;
			return getControlId().equals(otherWorkItem.getControlId()) && title.equals(otherWorkItem.title) 
																	   && user.equals(otherWorkItem.user) 
													 				   && description.equals(otherWorkItem.description) 
													 				   && status.equals(otherWorkItem.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += getControlId().hashCode() * 37;
		result += title.hashCode() * 37;
		result += user.hashCode() * 37;
		result += description.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "WorkItem [id=" + getId() + ", title=" + title + ", user=" + user + ", description=" + description + ", status=" + status + "]";
	}
}
