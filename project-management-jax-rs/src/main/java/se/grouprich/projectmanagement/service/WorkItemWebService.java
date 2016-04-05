package se.grouprich.projectmanagement.service;

import org.apache.commons.lang3.EnumUtils;
import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.exception.InvalidStatusException;
import se.grouprich.projectmanagement.exception.InvalidValueException;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.model.WorkItemData;
import se.grouprich.projectmanagement.model.mapper.WorkItemMapper;
import se.grouprich.projectmanagement.status.WorkItemStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@Path("/work-item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class WorkItemWebService
{
	private static final WorkItemService workItemService = Loader.getBean(WorkItemService.class);
	private static final UserService userService = Loader.getBean(UserService.class);
	private static final TeamService teamService = Loader.getBean(TeamService.class);
	private static final WorkItemMapper workItemMapper = new WorkItemMapper();

	@Context
	private UriInfo uriInfo;

	@POST
	public Response createWorkItem(WorkItem workItem) throws InvalidValueException
	{
		WorkItemData createdWorkItem = workItemService.createOrUpdate(workItemMapper.convertWorkItemToWorkItemData(workItem));
		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getWorkItem").build(createdWorkItem.getId());

		return Response.created(location).build();
	}

	@GET
	@Path("{id}")
	public Response getWorkItem(@PathParam("id") Long id) throws RepositoryException
	{
		WorkItemData workItemData = workItemService.findById(id);
		WorkItem workItem = workItemMapper.convertWorkItemDataToWorkItem(workItemData);

		return Response.ok(workItem).build();
	}

	@PUT
	@Path("{id}/status/{status}")
	public Response changeWorkItemStatus(@PathParam("id") Long id, @PathParam("status") WorkItemStatus status) throws RepositoryException, InvalidValueException
	{
		WorkItemData workItemData = workItemService.findById(id);

		if (!EnumUtils.isValidEnum(WorkItemStatus.class, status.toString()))
		{
			throw new InvalidStatusException(status.toString());
		}
		workItemService.changeWorkItemStatus(workItemData, status);

		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteWorkItem(@PathParam("id") Long id) throws RepositoryException
	{
		WorkItemData workItemData = workItemService.findById(id);
		workItemService.removeWorkItem(workItemData);

		return Response.noContent().build();
	}

	@PUT
	@Path("{workItemId}/user/{userId}")
	public Response assignWorkItemToUser(@PathParam("workItemId") Long workItemId, @PathParam("userId") Long userId)
			throws RepositoryException, InvalidValueException
	{
		UserData userData = userService.findById(userId);
		WorkItemData workItemData = workItemService.findById(workItemId);
		workItemService.assignWorkItemToUser(userData, workItemData);

		return Response.noContent().build();
	}

	@GET
	@Path("status/{status}")
	public Response getWorkItemsByStatus(@PathParam("status") WorkItemStatus status) throws RepositoryException
	{
		if (!EnumUtils.isValidEnum(WorkItemStatus.class, status.toString()))
		{
			throw new InvalidStatusException(status.toString());
		}
		List<WorkItemData> workItemDataList = workItemService.fetchWorkItemsByStatus(status);
		GenericEntity<Collection<WorkItem>> workItems = workItemMapper.convertList(workItemDataList);

		return Response.ok(workItems).build();
	}

	@GET
	@Path("team/{teamId}")
	public Response getWorkItemsForTeam(@PathParam("teamId") Long teamId) throws RepositoryException
	{
		TeamData teamData = teamService.findById(teamId);
		List<WorkItemData> workItemDataList = workItemService.fetchWorkItemsForTeam(teamData);
		GenericEntity<Collection<WorkItem>> workItems = workItemMapper.convertList(workItemDataList);

		return Response.ok(workItems).build();
	}

	@GET
	@Path("user/{userId}")
	public Response getWorkItemsForUser(@PathParam("userId") Long userId) throws RepositoryException
	{
		UserData userData = userService.findById(userId);
		List<WorkItemData> workItemDataList = workItemService.fetchWorkItemsForUser(userData);
		GenericEntity<Collection<WorkItem>> workItems = workItemMapper.convertList(workItemDataList);

		return Response.ok(workItems).build();
	}

	@GET
	@Path("search")
	public Response searchWorkItemsByDescriptionContaining(@QueryParam("keyword") String keyword) throws RepositoryException
	{
		List<WorkItemData> workItemDataList = workItemService.searchWorkItemsByDescriptionContaining(keyword);
		GenericEntity<Collection<WorkItem>> workItems = workItemMapper.convertList(workItemDataList);

		return Response.ok(workItems).build();
	}
}



