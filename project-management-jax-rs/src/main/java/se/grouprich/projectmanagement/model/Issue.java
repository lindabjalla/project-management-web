package se.grouprich.projectmanagement.model;

public class Issue extends AbstractEntity
{
	private String description;
	private WorkItem workItem;

	protected Issue(){}

	public Issue(Long id, String controlNumber, String description)
	{
		super(id, controlNumber);
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public WorkItem getWorkItem()
	{
		return workItem;
	}
	
	public void setDescription(final String description)
	{
		this.description = description;
	}

	public void setWorkItem(WorkItem workItem)
	{
		this.workItem = workItem;
	}
	
	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof Issue)
		{
			Issue otherIssue = (Issue) other;
			return getControlNumber().equals(otherIssue.getControlNumber()) && description.equals(otherIssue.description);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += getControlNumber().hashCode() * 37;
		result += description.hashCode() * 37;
		
		return result;
	}

	@Override
	public String toString()
	{
		return "Issue [id=" + getId() + ", controlNumber=" + getControlNumber() + ", description=" + description + ", workItem=" + workItem + "]";
	}
}
