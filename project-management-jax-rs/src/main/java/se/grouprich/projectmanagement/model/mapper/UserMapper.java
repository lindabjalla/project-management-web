package se.grouprich.projectmanagement.model.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.service.TeamService;
import se.grouprich.projectmanagement.status.UserStatus;

import java.util.ArrayList;
import java.util.List;

public final class UserMapper
{
	private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	private final MapperFacade mapper = mapperFactory.getMapperFacade();
	private static final TeamService teamService = Loader.getBean(TeamService.class);

	public UserMapper()
	{
		mapperFactory.classMap(User.class, UserData.class).fieldBToA("id", "id").exclude("status").byDefault().register();
	}

	public UserData convertUserToUserData(final User user)
	{
		UserData userData = mapper.map(user, UserData.class);
		return userData;
	}

	public User convertUserDataToUser(final UserData userData)
	{
		User user = mapper.map(userData, User.class);

		user.setStatus(userData.getStatus().toString());
		if (userData.getTeam() != null)
		{
			user.setTeamId(userData.getTeam().getId());
		}
		return user;
	}

	public UserData updateUserData(final User user, final UserData userData) throws RepositoryException
	{
		userData.setUsername(user.getUsername()).setPassword(user.getPassword()).setStatus(UserStatus.valueOf(user.getStatus()));
		if (user.getTeamId() != null)
		{
			TeamData teamData = teamService.findById(user.getTeamId());
			if (teamData != null)
			{
				userData.setTeam(teamData);
			}
		}

		return userData;
	}

	public List<User> convertList(List<UserData> userDataList)
	{
		List<User> users = new ArrayList<>();
		userDataList.forEach(userData -> users.add(convertUserDataToUser(userData)));
		return users;
	}
}


