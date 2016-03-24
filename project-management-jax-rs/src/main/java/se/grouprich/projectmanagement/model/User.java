package se.grouprich.projectmanagement.model;

public final class User extends AbstractEntity
{
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String status;
	private String team;

	public User(){}

	public User(final Long id, final String username, final String password, final String firstName, final String lastName, String controlNumber, String status, String team)
	{
		super(id, controlNumber);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.team = team;
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

	public String getStatus()
	{
		return status;
	}

	public String getTeam()
	{
		return team;
	}

	public User setUsername(final String username)
	{
		this.username = username;
		return this;
	}

	public User setPassword(final String password)
	{
		this.password = password;
		return this;
	}

	public User setStatus(final String status)
	{
		this.status = status;
		return this;
	}

/*	public User setTeam(final Team team)
	{
		if (this.team == null || !this.team.equals(team))
		{
			this.team = team;
			team.addUser(this);
		}
		return this;
	}*/

	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other instanceof User)
		{
			User otherUser = (User) other;
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
