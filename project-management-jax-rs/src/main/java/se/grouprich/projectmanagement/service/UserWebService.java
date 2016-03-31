package se.grouprich.projectmanagement.service;

import com.google.common.collect.Lists;
import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.exception.UserException;
import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.model.mapper.UserMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class UserWebService
{
	private static final UserService userService = Loader.getBean(UserService.class);
	private static final TeamService teamService = Loader.getBean(TeamService.class);
	private static final UserMapper userMapper = new UserMapper();

	@Context
	private UriInfo uriInfo;

	@POST
	public Response createUser(User user) throws UserException
	{
		UserData createdUser = userService.createOrUpdate(userMapper.convertUserToUserData(user));
		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getUser").build(createdUser.getId());

		return Response.created(location).build();
	}

	@GET
	@Path("{id}")
	public Response getUser(@PathParam("id") Long id) throws RepositoryException
	{
		UserData userData = userService.findById(id);
		User user = userMapper.convertUserDataToUser(userData);

		return Response.ok(user).build();
	}

	@PUT
	@Path("{id}")
	public Response updateUser(@PathParam("id") Long id, User user) throws UserException, RepositoryException
	{
		UserData userData = userService.findById(id);
		UserData updatedUserData = userMapper.updateUserData(user, userData);
		userService.createOrUpdate(updatedUserData);

		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") Long id) throws RepositoryException
	{
		userService.deleteById(id);
		return Response.noContent().build();
	}

	@GET
	@Path("control-id/{controlId}")
	public Response getUserByControlNumber(@PathParam("controlId") String controlId) throws RepositoryException
	{
		UserData userData = userService.findByControlId(controlId);
		User user = userMapper.convertUserDataToUser(userData);

		return Response.ok(user).build();
	}

	@GET
	@Path("query")
	public Response getUserByAnyName(@QueryParam("first-name") String firstName, @QueryParam("last-name") String lastName,
			@QueryParam("username") String username) throws RepositoryException
	{
		List<UserData> userDataList = userService.searchUsersByFirstNameOrLastNameOrUsername(firstName, lastName, username);
		List<User> users = userMapper.convertList(userDataList);

		return Response.ok(users).build();
	}

	@GET
	public Response getAllUsers() throws RepositoryException
	{
		Iterable<UserData> userDataIterable = userService.findAll();
		List<UserData> userDataList = Lists.newArrayList(userDataIterable);
		List<User> users = userMapper.convertList(userDataList);

		return Response.ok(users).build();
	}

	@GET
	@Path("team/{teamId}")
	public Response getUsersByTeam(@PathParam("teamId") Long teamId) throws RepositoryException
	{
		TeamData teamData = teamService.findById(teamId);
		List<UserData> userDataList = userService.findByTeam(teamData);
		List<User> users = userMapper.convertList(userDataList);

		return Response.ok(users).build();
	}
}
