package se.grouprich.projectmanagement.model;

import java.util.Set;

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
			return getControlNumber().equals(otherTeam.getControlNumber()) && name.equals(otherTeam.name) && status.equals(otherTeam.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += getControlNumber().hashCode() * 37;
		result += name.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "Team [id=" + getId() + ", controlNumber=" + getControlNumber() + ", name=" + name + ", status=" + status + "]";
	}
}
