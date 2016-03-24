package se.grouprich.projectmanagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import se.grouprich.projectmanagement.status.TeamStatusData;

@Entity
public class TeamData extends AbstractEntityData
{
	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "team", cascade = CascadeType.MERGE)
	private Set<UserData> users;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TeamStatusData status;

	protected TeamData()
	{
	}

	public TeamData(final String name)
	{
		super();
		this.name = name;
		status = TeamStatusData.ACTIVE;
		users = new HashSet<>();
	}

	public String getName()
	{
		return name;
	}

	public Set<UserData> getUsers()
	{
		return users;
	}

	public TeamStatusData getStatus()
	{
		return status;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public void setStatus(final TeamStatusData status)
	{
		this.status = status;
	}

	public TeamData addUser(final UserData user)
	{
		if (!users.contains(user))
		{
			user.setTeam(this);
			users.add(user);
		}
		return this;
	}

	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof TeamData)
		{
			TeamData otherTeam = (TeamData) other;
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
