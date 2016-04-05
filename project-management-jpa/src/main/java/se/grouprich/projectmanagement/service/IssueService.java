package se.grouprich.projectmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.grouprich.projectmanagement.exception.InvalidValueException;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.model.IssueData;
import se.grouprich.projectmanagement.model.WorkItemData;
import se.grouprich.projectmanagement.repository.IssueRepository;
import se.grouprich.projectmanagement.status.WorkItemStatus;

@Service
public class IssueService extends AbstractService<IssueData, IssueRepository>
{
	@Autowired
	IssueService(final IssueRepository issueRepository)
	{
		super(issueRepository, IssueData.class);
	}
	
	@Override
	public IssueData createOrUpdate(final IssueData issueData) throws InvalidValueException 
	{
		return super.createOrUpdate(issueData);
	}

	@Transactional
	public IssueData addIssueToWorkItem(final IssueData issue, final WorkItemData workItem) throws InvalidValueException
	{
		if (workItem == null)
		{
			throw new InvalidValueException("WorkItem must not be null");
		}
		if (!WorkItemStatus.DONE.equals(workItem.getStatus()))
		{
			throw new InvalidValueException("An Issue can only be added to a WorkItem with WorkItemStatus.DONE");
		}

		IssueData issueAddedToWorkItem = issue.setWorkItem(workItem);
		workItem.setStatus(WorkItemStatus.UNSTARTED);

		return createOrUpdate(issueAddedToWorkItem);
	}

//	public IssueData updateIssue(IssueData issue) throws RepositoryException, InvalidValueException
//	{
//		if (issue.getId() == null)
//		{
//			throw new RepositoryException("Issue does not exist");
//		}
//		
//		return createOrUpdate(issue);
//	}
}