package se.grouprich.projectmanagement.model;

import java.util.Collection;

import se.grouprich.projectmanagement.status.TeamStatus;

public final class Team extends AbstractEntity
{
	private String name;
	private Collection<User> users;
	private String status;
	private String controlNumber;

	public Team(){}

	public Team(Long id, String name, String status, Collection<User> users, String controlNumber)
	{
		super(id, controlNumber);
		this.name = name;
		this.status = status;
		this.users = users;
		this.controlNumber = controlNumber;
	}

	public String getName()
	{
		return name;
	}

	public Collection<User> getUsers()
	{
		return users;
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

/*
	public Team addUser(final User user)
	{
		if (!users.contains(user))
		{
			user.setTeam(this);
			users.add(user);
		}
		return this;
	}
*/

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
			return controlNumber.equals(otherTeam.controlNumber) && name.equals(otherTeam.name) && status.equals(otherTeam.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += controlNumber.hashCode() * 37;
		result += name.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "Team [id=" + getId() + ", controlNumber=" + controlNumber + ", name=" + name + ", status=" + status + "]";
	}
}
