package se.grouprich.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.exception.WorkItemException;
import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.model.WorkItemData;
import se.grouprich.projectmanagement.repository.IssueRepository;
import se.grouprich.projectmanagement.repository.UserRepository;
import se.grouprich.projectmanagement.repository.WorkItemRepository;
import se.grouprich.projectmanagement.status.UserStatus;
import se.grouprich.projectmanagement.status.WorkItemStatus;

@Service
public class WorkItemService extends AbstractService<WorkItemData, WorkItemRepository>
{
//	private IssueRepository issueRepository;
	private UserRepository userRepository;

	@Autowired
	WorkItemService(final WorkItemRepository workItemRepository, final IssueRepository issueRepository, final UserRepository userRepository)
	{
		super(workItemRepository);
//		this.issueRepository = issueRepository;
		this.userRepository = userRepository;
	}

	public WorkItemData createOrUpdate(final WorkItemData workItem)
	{
		return super.createOrUpdate(workItem);
	}

	public WorkItemData changeWorkItemStatus(final WorkItemData workItem, final WorkItemStatus status)
	{
		workItem.setStatus(status);
		return createOrUpdate(workItem);
	}

	@Transactional
	public WorkItemData removeWorkItem(final WorkItemData workItem)
	{
//		issueRepository.removeByWorkItem(workItem);
		return superRepository.removeById(workItem.getId()).get(0);
	}

	@Transactional
	public WorkItemData assignWorkItemToUser(final UserData user, final WorkItemData workItem) throws WorkItemException
	{
		final UserData savedUser = userRepository.save(user);
		if (!UserStatus.ACTIVE.equals(savedUser.getStatus()))
		{
			throw new WorkItemException("A WorkItem can only be assigned to a User with UserStatus.ACTIVE");
		}

		final List<WorkItemData> workItemsFoundByUser = superRepository.findByUser(savedUser);
		if (workItemsFoundByUser.size() >= 5)
		{
			throw new WorkItemException("Maximum number of work items a User can have is 5");
		}

		final WorkItemData assignedWorkItem = workItem.setUser(savedUser);
		return createOrUpdate(assignedWorkItem);
	}

	public List<WorkItemData> fetchWorkItemsByStatus(final WorkItemStatus status)
	{
		return superRepository.findByStatus(status);
	}

	public List<WorkItemData> fetchWorkItemsForTeam(final TeamData team)
	{
		return superRepository.findByTeam(team);
	}

	public List<WorkItemData> fetchWorkItemsForUser(final UserData user)
	{
		return superRepository.findByUser(user);
	}

	public List<WorkItemData> searchWorkItemsByDescriptionContaining(final String keyword)
	{
		return superRepository.findByDescriptionContaining(keyword);
	}
}
