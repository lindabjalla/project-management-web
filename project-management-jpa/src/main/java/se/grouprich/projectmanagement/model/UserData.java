package se.grouprich.projectmanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import se.grouprich.projectmanagement.status.UserStatusData;

@Entity
public class UserData extends AbstractEntityData
{
	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatusData status;

	@ManyToOne(cascade = CascadeType.MERGE)
	private TeamData team;

	protected UserData(){}

	public UserData(final String username, final String password, final String firstName, final String lastName)
	{
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		status = UserStatusData.ACTIVE;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public UserStatusData getStatus()
	{
		return status;
	}

	public TeamData getTeam()
	{
		return team;
	}

	public UserData setUsername(final String username)
	{
		this.username = username;
		return this;
	}

	public UserData setPassword(final String password)
	{
		this.password = password;
		return this;
	}

	public UserData setStatus(final UserStatusData status)
	{
		this.status = status;
		return this;
	}

	public UserData setTeam(final TeamData team)
	{
		if (this.team == null || !this.team.equals(team))
		{
			this.team = team;
			team.addUser(this);
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
		if (other instanceof UserData)
		{
			UserData otherUser = (UserData) other;
			return getControlNumber().equals(otherUser.getControlNumber()) && username.equals(otherUser.username)
														   				   && password.equals(otherUser.password)
														   				   && firstName.equals(otherUser.firstName)
														   				   && lastName.equals(otherUser.lastName)
														   				   && status.equals(otherUser.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += getControlNumber().hashCode() * 37;
		result += username.hashCode() * 37;
		result += password.hashCode() * 37;
		result += firstName.hashCode() * 37;
		result += lastName.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "User [id=" + getId() + ", userNumber=" + getControlNumber() + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", status=" + status + ", team=" + team + "]";
	}
}
