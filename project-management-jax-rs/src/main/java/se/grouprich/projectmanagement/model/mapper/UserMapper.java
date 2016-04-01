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
		mapperFactory.classMap(User.class, UserData.class).exclude("id").exclude("team").byDefault().register();
	}

	public UserData convertUserToUserData(final User user)
	{
		return mapper.map(user, UserData.class);
	}

	public User convertUserDataToUser(final UserData userData)
	{
		return mapper.map(userData, User.class);
	}

	public UserData updateUserData(final User user, final UserData userData) throws RepositoryException
	{
		userData.setUsername(user.getUsername()).setPassword(user.getPassword()).setStatus(user.getStatus());
		return userData;
	}

	public List<User> convertList(List<UserData> userDataList)
	{
		List<User> users = new ArrayList<>();
		userDataList.forEach(userData -> users.add(convertUserDataToUser(userData)));
		return users;
	}
}


