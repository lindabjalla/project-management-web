package se.grouprich.projectmanagement.service;

//import se.grouprich.projectmanagement.ContextLoader;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.model.mapper.UserMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class UserWebService
{
	private static final UserService userService = Loader.getBean(UserService.class);
	private static final TeamService teamService = Loader.getBean(TeamService.class);
	private static final UserMapper userMapper = new UserMapper();
	//	private static UserService userService = ContextLoader.getBean(UserService.class);

	@Context
	private UriInfo uriInfo;

	@POST
	public Response createUser(User user)
	{
		UserData createdUser = userService.createOrUpdate(userMapper.convertUserToUserData(user));
		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getUser").build(createdUser.getId());

		return Response.created(location).build();
	}

	@GET
	@Path("{id}")
	public Response getUser(@PathParam("id") Long id)
	{
		UserData userData = userService.findById(id);

		if (userData == null)
		{
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		User user = userMapper.convertUserDataToUser(userData);

		return Response.ok(user).build();
	}

	@PUT
	@Path("{id}")
	public Response updateUser(@PathParam("id") Long id, User user)
	{
		UserData userData = userService.findById(id);
		if (userData == null)
		{
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		UserData updatedUserData = userMapper.updateUserData(user, userData);
		userService.createOrUpdate(updatedUserData);

		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") Long id)
	{
		if (userService.findById(id) == null)
		{
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		userService.deleteById(id);
		return Response.noContent().build();
	}

	@GET
	@Path("user-number/{controlNumber}")
	public Response getUserByControlNumber(@PathParam("controlNumber") String controlNumber)
	{
		UserData userData = userService.findByControlNumber(controlNumber);

		if (userData == null)
		{
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		User user = userMapper.convertUserDataToUser(userData);

		return Response.ok(user).build();
	}

	@GET
	@Path("query")
	public Response getUserByAnyName(@QueryParam("first-name") String firstName, @QueryParam("last-name") String lastName,
			@QueryParam("username") String username)
	{
		List<UserData> userDataList = userService.searchUserByFirstNameOrLastNameOrUsername(firstName, lastName, username);

		if (userDataList.isEmpty())
		{
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		GenericEntity<List<User>> users = userMapper.convertList(userDataList);

		return Response.ok(users).build();
	}

	@GET
	public Response getAllUsers()
	{
		Iterable<UserData> userDataIterable = userService.findAll();
		if (Iterables.isEmpty(userDataIterable))
		{
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		List<UserData> userDataList = Lists.newArrayList(userDataIterable);
		GenericEntity<List<User>> users = userMapper.convertList(userDataList);

		return Response.ok(users).build();
	}

	@GET
	@Path("team/{teamId}")
	public Response getUsersByTeam(@PathParam("teamId") Long teamId)
	{
		TeamData teamData = teamService.findById(teamId);
		List<UserData> userDataList = userService.findByTeam(teamData);
		GenericEntity<List<User>> users = userMapper.convertList(userDataList);

		return Response.ok(users).build();
	}
}
