package se.grouprich.projectmanagement.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.exception.UserException;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.model.mapper.TeamMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/team")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamWebService
{
	private static final TeamService teamService = Loader.getBean(TeamService.class);
	private static final UserService userService = Loader.getBean(UserService.class);
	private static final TeamMapper teamMapper = new TeamMapper();

	@Context
	private UriInfo uriInfo;

	@POST
	public Response createTeam(Team team) throws UserException
	{
		TeamData createdTeam = teamService.createOrUpdate(teamMapper.convertTeamToTeamData(team));
		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getTeam").build(createdTeam.getId());

		return Response.created(location).build();
	}

	@GET
	@Path("{id}")
	public Response getTeam(@PathParam("id") Long id) throws RepositoryException
	{
		TeamData teamData = teamService.findById(id);
		Team team = teamMapper.convertTeamDataToTeam(teamData);

		return Response.ok(team).build();
	}

	@PUT
	@Path("{id}")
	public Response updateTeam(@PathParam("id") Long id, Team team) throws UserException, RepositoryException
	{
		TeamData teamData = teamService.findById(id);
		TeamData updateTeamData = teamMapper.updateTeamData(team, teamData);
		teamService.createOrUpdate(updateTeamData);

		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteTeam(@PathParam("id") Long id) throws RepositoryException
	{
		teamService.deleteById(id);
		return Response.noContent().build();
	}

	@GET
	public Response getAllTeams() throws RepositoryException
	{
		Iterable<TeamData> teamDataIterable = teamService.findAll();
		List<TeamData> teamDataList = Lists.newArrayList(teamDataIterable);
		List<Team> teams = teamMapper.convertList(teamDataList);

		return Response.ok(teams).build();
	}

	@PUT
	@Path("{teamId}/user/{userId}")
	public Response addUserToTeam(@PathParam("teamId") Long teamId, @PathParam("userId") Long userId) throws TeamException, RepositoryException, UserException
	{
		TeamData teamData = teamService.findById(teamId);
		UserData userData = userService.findById(userId);
		teamService.addUserToTeam(teamData, userData);

		return Response.noContent().build();
	}
}
