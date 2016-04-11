package se.grouprich.projectmanagement.model.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.UserData;

import javax.ws.rs.core.GenericEntity;
import java.util.ArrayList;
import java.util.List;

public final class UserMapper
{
	private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	private final MapperFacade mapper = mapperFactory.getMapperFacade();

	public UserMapper()
	{
		mapperFactory.classMap(User.class, UserData.class).exclude("team").byDefault().register();
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

	public GenericEntity<List<User>> convertList(final List<UserData> userDataList)
	{
		List<User> users = new ArrayList<>();
		userDataList.forEach(userData -> users.add(convertUserDataToUser(userData)));

		return new GenericEntity<List<User>>(users){};
	}
}


