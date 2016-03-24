package se.grouprich.projectmanagement.service;

//import se.grouprich.projectmanagement.ContextLoader;
import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.UserData;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class UserWebService
{
	private static UserService userService = Loader.getBean(UserService.class);
//	private static UserService userService = ContextLoader.getBean(UserService.class);

	@Context
	private UriInfo uriInfo;

	@POST
	public Response addUser(User user)
	{
		UserData createdUser = userService.createOrUpdate(
				new UserData(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName()));

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
			throw new WebApplicationException(404);
		}

		User user = new User(userData.getId(), userData.getUsername(), userData.getPassword(), userData.getFirstName(),
				userData.getLastName(), userData.getControlNumber(), userData.getStatus().toString(), "team1");

		return Response.ok(user).build();
	}
}
