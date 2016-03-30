package se.grouprich.projectmanagement.model;

public final class Team extends AbstractEntity
{
	private String name;
	private String status;

	public Team(){}

	public Team(Long id, String controlNumber, String name, String status)
	{
		super(id, controlNumber);
		this.name = name;
		this.status = status;
	}

	public String getName()
	{
		return name;
	}

	public String getStatus()
	{
		return status;
	}

	public void setName(final String name)
	{
		this.name = name;
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

		if (other instanceof Team)
		{
			Team otherTeam = (Team) other;
			return getControlId().equals(otherTeam.getControlId()) && name.equals(otherTeam.name) && status.equals(otherTeam.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += getControlId().hashCode() * 37;
		result += name.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "Team [id=" + getId() + ", controlNumber=" + getControlId() + ", name=" + name + ", status=" + status + "]";
	}
}
