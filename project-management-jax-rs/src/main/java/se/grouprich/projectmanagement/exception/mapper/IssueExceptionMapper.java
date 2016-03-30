package se.grouprich.projectmanagement.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import se.grouprich.projectmanagement.exception.IssueException;

public class IssueExceptionMapper implements ExceptionMapper<IssueException>
{
	@Override
	public Response toResponse(IssueException issueException) 
	{
		return Response.status(Status.BAD_REQUEST).entity(issueException.getMessage()).build();
	}
}
