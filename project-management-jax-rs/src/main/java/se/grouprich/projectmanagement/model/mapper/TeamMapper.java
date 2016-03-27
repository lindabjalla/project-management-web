package se.grouprich.projectmanagement.model.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.service.TeamService;
import se.grouprich.projectmanagement.status.TeamStatus;

import javax.ws.rs.core.GenericEntity;
import java.util.ArrayList;
import java.util.List;

public final class TeamMapper
{
	private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	private final MapperFacade mapper = mapperFactory.getMapperFacade();
	private static TeamService teamService = Loader.getBean(TeamService.class);

	public TeamMapper()
	{
		mapperFactory.classMap(Team.class, TeamData.class).fieldBToA("id", "id").exclude("status").byDefault().register();
	}

	public TeamData convertTeamToTeamData(final Team team)
	{
		TeamData teamData = mapper.map(team, TeamData.class);
		return teamData;
	}

	public Team convertTeamDataToTeam(final TeamData teamData)
	{
		Team team = mapper.map(teamData, Team.class);
		team.setStatus(teamData.getStatus().toString());
		return team;
	}

	public TeamData updateTeamData(final Team team, final TeamData teamData)
	{
		teamData.setName(team.getName()).setStatus(TeamStatus.valueOf(team.getStatus()));
		return teamData;
	}

	public GenericEntity<List<Team>> convertList(List<TeamData> teamDataList)
	{
		List<Team> teams = new ArrayList<>();
		teamDataList.forEach(teamData -> teams.add(convertTeamDataToTeam(teamData)));
		GenericEntity<List<Team>> entity = new GenericEntity<List<Team>>(teams) {};
		return entity;
	}
}
