package se.grouprich.projectmanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import se.grouprich.projectmanagement.status.WorkItemStatus;

@Entity
public class WorkItemData extends AbstractEntityData
{
	@Column(nullable = false)
	private String title;

	@ManyToOne(cascade = CascadeType.MERGE)
	private UserData user;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private WorkItemStatus status;

	protected WorkItemData(){}

	public WorkItemData(final String title)
	{
		super();
		this.title = title;
		status = WorkItemStatus.UNSTARTED;
	}

	public UserData getUser()
	{
		return user;
	}

	public WorkItemStatus getStatus()
	{
		return status;
	}
	
	public void setTitle(final String title)
	{
		this.title = title;
	}

	public WorkItemData setStatus(final WorkItemStatus status)
	{
		this.status = status;
		return this;
	}

	public WorkItemData setDescription(final String description)
	{
		this.description = description;
		return this;
	}

	public WorkItemData setUser(final UserData user)
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

		if (other instanceof WorkItemData)
		{
			WorkItemData otherWorkItem = (WorkItemData) other;
			return getControlId().equals(otherWorkItem.getControlId()) && title.equals(otherWorkItem.title)
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
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "WorkItem [id=" + getId() + ", controlId=" + getControlId() + ", title=" + title + ", user=" + user + ", description=" + description + ", status=" + status + "]";
	}
}
