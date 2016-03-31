package se.grouprich.projectmanagement.model;

public final class WorkItem extends AbstractEntity
{
	private String title;
	private User user;
	private String description;
	private String status;

	protected WorkItem() {}

	public WorkItem(Long id, String controlId, String title, User user, String description, String status)
	{
		super(id, controlId);
		this.title = title;
		this.user = user;
		this.description = description;
		this.status = status;
	}

	public String getTitle()
	{
		return title;
	}

	public User getUser()
	{
		return user;
	}

	public String getDescription()
	{
		return description;
	}

	public String getStatus()
	{
		return status;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public WorkItem setUser(User user)
	{
		this.user = user;
		return this;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setStatus(final String status)
	{
		this.status = status;
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
