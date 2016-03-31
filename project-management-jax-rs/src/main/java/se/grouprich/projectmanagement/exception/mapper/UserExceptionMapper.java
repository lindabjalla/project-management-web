package se.grouprich.projectmanagement.exception.mapper;

import se.grouprich.projectmanagement.exception.UserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status;

@Provider
public final class UserExceptionMapper implements ExceptionMapper<UserException>
{
	@Override
	public Response toResponse(UserException exception)
	{
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}