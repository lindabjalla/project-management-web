package se.grouprich.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.model.WorkItemData;
import se.grouprich.projectmanagement.repository.UserRepository;
import se.grouprich.projectmanagement.repository.WorkItemRepository;
import se.grouprich.projectmanagement.status.UserStatusData;
import se.grouprich.projectmanagement.status.WorkItemStatusData;

@Service
public class UserService extends AbstractService<UserData, UserRepository>
{
	private WorkItemRepository workItemRepository;

	@Autowired
	UserService(final UserRepository superRepository, final WorkItemRepository workItemRepository)
	{
		super(superRepository);
		this.workItemRepository = workItemRepository;
	}

	public UserData createOrUpdate(final UserData user)
	{
		if (!hasValidLength(user.getUsername()))
		{
			throw new IllegalArgumentException("Username must be longer than or equal to 10 characters");
		}
		return super.createOrUpdate(user);
	}

	@Transactional
	public UserData inactivateUser(final UserData user)
	{
		user.setStatus(UserStatusData.INACTIVE);

		final List<WorkItemData> workItemsfoundByUser = workItemRepository.findByUser(user);
		for (WorkItemData workItem : workItemsfoundByUser)
		{
			workItem.setStatus(WorkItemStatusData.UNSTARTED);
			workItemRepository.save(workItem);
		}

		return createOrUpdate(user);
	}

	public UserData findByControlNumber(final Long controlNumber)
	{
		return superRepository.findByControlNumber(controlNumber);
	}

	public UserData searchUserByFirstNameAndLastNameAndUsername(final String firstName, final String lastName, final String username)
	{
		return superRepository.findByFirstNameAndLastNameAndUsername(firstName, lastName, username);
	}

	public List<UserData> searchUserByFirstNameOrLastNameOrUsername(final String firstName, final String lastName, final String username)
	{
		return superRepository.findAllByFirstNameOrLastNameOrUsername(firstName, lastName, username);
	}

	public List<UserData> findByTeam(final TeamData team)
	{
		return superRepository.findByTeam(team);
	}

	private boolean hasValidLength(final String username)
	{
		if (username != null && !username.trim().isEmpty())
		{
			if (username.length() >= 10)
			{
				return true;
			}
		}
		return false;
	}
}