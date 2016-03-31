package se.grouprich.projectmanagement.exception.mapper;

import se.grouprich.projectmanagement.exception.WorkItemException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WorkItemExceptionMapper implements ExceptionMapper<WorkItemException>
{
	@Override public Response toResponse(WorkItemException exception)
	{
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}
