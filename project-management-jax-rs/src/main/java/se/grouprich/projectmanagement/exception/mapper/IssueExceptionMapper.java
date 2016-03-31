package se.grouprich.projectmanagement.exception.mapper;

import se.grouprich.projectmanagement.exception.IssueException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status;

@Provider
public final class IssueExceptionMapper implements ExceptionMapper<IssueException>
{
	@Override
	public Response toResponse(IssueException issueException)
	{
		return Response.status(Status.BAD_REQUEST).entity(issueException.getMessage()).build();
	}
}
