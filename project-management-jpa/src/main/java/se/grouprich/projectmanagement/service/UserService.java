package se.grouprich.projectmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.grouprich.projectmanagement.exception.UserException;
import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.model.WorkItemData;
import se.grouprich.projectmanagement.repository.UserRepository;
import se.grouprich.projectmanagement.repository.WorkItemRepository;
import se.grouprich.projectmanagement.status.UserStatus;
import se.grouprich.projectmanagement.status.WorkItemStatus;

import java.util.List;

@Service
public class UserService extends AbstractService<UserData, UserRepository>
{
	private WorkItemRepository workItemRepository;

	@Autowired
	UserService(final UserRepository userRepository, WorkItemRepository workItemRepository)
	{
		super(userRepository);
		this.workItemRepository = workItemRepository;
	}

	@Override
	public UserData createOrUpdate(final UserData user) throws UserException
	{
		if (user.getUsername() == null || user.getUsername().trim().length() < 10)
		{
			throw new UserException("Username must be longer than or equal to 10 characters");
		}
		return super.createOrUpdate(user);
	}

	public UserData findByControlNumber(final String controlNumber)
	{
		return superRepository.findByControlNumber(controlNumber);
	}

	public List<UserData> searchUserByFirstNameOrLastNameOrUsername(final String firstName, final String lastName, final String username)
	{
		return superRepository.findAllByFirstNameOrLastNameOrUsername(firstName, lastName, username);
	}

	public List<UserData> findByTeam(final TeamData team)
	{
		return superRepository.findByTeam(team);
	}

	@Transactional
	public UserData inactivateUser(final UserData user) throws UserException
	{
		user.setStatus(UserStatus.INACTIVE);

		final List<WorkItemData> workItemsFoundByUser = workItemRepository.findByUser(user);
		for (WorkItemData workItem : workItemsFoundByUser)
		{
			workItem.setStatus(WorkItemStatus.UNSTARTED);
			workItemRepository.save(workItem);
		}

		return createOrUpdate(user);
	}
}