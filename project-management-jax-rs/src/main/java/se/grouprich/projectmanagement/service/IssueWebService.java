package se.grouprich.projectmanagement.service;

import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.exception.InvalidValueException;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.model.Issue;
import se.grouprich.projectmanagement.model.IssueData;
import se.grouprich.projectmanagement.model.mapper.IssueMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/issue")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IssueWebService
{
	private static final IssueService issueService = Loader.getBean(IssueService.class);
	private static final IssueMapper issueMapper = new IssueMapper();

	@Context
	private UriInfo uriInfo;

	@POST
	public Response createIssue(Issue issue) throws InvalidValueException
	{
		IssueData createdIssue = issueService.createOrUpdate(issueMapper.convertIssueToIssueData(issue));
		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getIssue").build(createdIssue.getId());

		return Response.created(location).build();
	}

	@GET
	@Path("{id}")
	public Response getIssue(@PathParam("id") Long id) throws RepositoryException
	{
		IssueData issueData = issueService.findById(id);

		Issue issue = issueMapper.convertIssueDataToIssue(issueData);

		return Response.ok(issue).build();
	}

	//	@PUT //Lägg till en Issue till WorkItem
	//	@Path("{id}")
	//	public Response updateIssueByWorkItem(@PathParam("id") Long id, Issue issue) throws UserException, RepositoryException
	//	{
	//		IssueData issueData = issueService.findById(id);
	//
	//		if(issueData == null)
	//		{
	//			Response.status(Status.NOT_FOUND);
	//		}
	//
	//		IssueData updateIssueData = issueMapper.updateIssueData(issue, issueData);
	//		issueService.createOrUpdate(updateIssueData);
	//
	//		return Response.noContent().build();
	//	}

	@DELETE
	@Path("{id}")
	public Response deleteIssue(@PathParam("id") Long id) throws RepositoryException, InvalidValueException
	{
		if (issueService.findById(id) == null)
		{
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		issueService.deleteById(id);
		return Response.noContent().build();
	}

	@PUT
	@Path("{id}")
	public Response updateIssue(@PathParam("id") Long id, Issue issue) throws InvalidValueException, RepositoryException
	{
		IssueData issueData = issueService.findById(id);

		if (issueData == null)
		{
			Response.status(Status.NOT_FOUND);
		}

		IssueData updateIssueData = issueMapper.updateIssueData(issue, issueData);
		issueService.createOrUpdate(updateIssueData);

		return Response.noContent().build();
	}

	//	@GET
	//	public Response getAllWorkItemInIssue()
	//	{
	//
	//	}
}
