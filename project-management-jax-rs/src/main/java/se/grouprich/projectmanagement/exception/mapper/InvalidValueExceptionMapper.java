package se.grouprich.projectmanagement.exception.mapper;

import se.grouprich.projectmanagement.exception.InvalidValueException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status;

@Provider
public class InvalidValueExceptionMapper implements ExceptionMapper<InvalidValueException>
{
	@Override
	public Response toResponse(InvalidValueException exception)
	{
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}
