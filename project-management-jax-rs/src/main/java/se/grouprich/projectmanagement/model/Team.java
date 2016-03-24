package se.grouprich.projectmanagement.model;

import java.util.Collection;

import se.grouprich.projectmanagement.status.TeamStatus;

public final class Team extends AbstractEntity
{
	private String name;
	private Collection<User> users;
	private TeamStatus status;
	private String teamNumber;

	public Team(){}

	public Team(Long id, String name, TeamStatus status, Collection<User> users, String teamNumber, String controlNumber)
	{
		super(id, controlNumber);
		this.name = name;
		this.status = status;
		this.users = users;
		this.teamNumber = teamNumber;
	}

	public String getName()
	{
		return name;
	}

	public Collection<User> getUsers()
	{
		return users;
	}

	public TeamStatus getStatus()
	{
		return status;
	}
	
	public String getTeamNumber()
	{
		return teamNumber;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public void setStatus(final TeamStatus status)
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
			return teamNumber.equals(otherTeam.teamNumber) && name.equals(otherTeam.name) && status.equals(otherTeam.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += teamNumber.hashCode() * 37;
		result += name.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "Team [id=" + getId() + ", teamNumber=" + teamNumber + ", name=" + name + ", status=" + status + "]";
	}
}
