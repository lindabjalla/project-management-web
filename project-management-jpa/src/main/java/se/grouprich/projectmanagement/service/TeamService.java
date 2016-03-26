package se.grouprich.projectmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.repository.TeamRepository;
import se.grouprich.projectmanagement.repository.UserRepository;
import se.grouprich.projectmanagement.status.TeamStatus;

@Service
public class TeamService extends AbstractService<TeamData, TeamRepository>
{
	private UserRepository userRepository;

	@Autowired
	TeamService(final TeamRepository superRepository, final UserRepository userRepository)
	{
		super(superRepository);
		this.userRepository = userRepository;
	}

	public TeamData createOrUpdate(final TeamData team)
	{
		return super.createOrUpdate(team);
	}

	public TeamData inactivateTeam(final TeamData team)
	{
		team.setStatus(TeamStatus.INACTIVE);
		return createOrUpdate(team);
	}

	@Transactional
	public TeamData addUserToTeam(final TeamData team, final UserData user) throws TeamException, RepositoryException
	{
		userRepository.save(user);

		if (user.getTeam() != null)
		{
			throw new TeamException("User is already in a Team. A User can only be in one Team at a time");
		}
		if (team.getUsers().size() >= 10)
		{
			throw new TeamException("Maximum number of users in a Team is 10");
		}

		final TeamData teamUserAdded = team.addUser(user);
		return createOrUpdate(teamUserAdded);
	}
}
