package se.grouprich.projectmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.exception.IssueException;
import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.exception.UserException;
import se.grouprich.projectmanagement.exception.WorkItemException;
import se.grouprich.projectmanagement.model.IssueData;
import se.grouprich.projectmanagement.model.WorkItemData;
import se.grouprich.projectmanagement.repository.IssueRepository;
import se.grouprich.projectmanagement.status.WorkItemStatus;

@Service
public class IssueService extends AbstractService<IssueData, IssueRepository>
{
	private IssueRepository issueRepository;
	
	@Autowired
	IssueService(final IssueRepository issueRepository)
	{
		super(issueRepository, IssueData.class);
		this.issueRepository = issueRepository;
	}

	@Transactional
	public IssueData createAndAddToWorkItem(WorkItemData workItem, IssueData issue) throws WorkItemException, UserException
	{
		if (workItem == null)
		{
			throw new WorkItemException("WorkItem must not be null");
		}
		if (!WorkItemStatus.DONE.equals(workItem.getStatus()))
		{
			throw new WorkItemException("An Issue can only be added to a WorkItem with WorkItemStatus.DONE");
		}

		IssueData issueAddedToWorkItem = issue.setWorkItem(workItem);
		workItem.setStatus(WorkItemStatus.UNSTARTED);

		return createOrUpdate(issueAddedToWorkItem);
	}

	public IssueData updateIssue(IssueData issue) throws RepositoryException, UserException 
	{
		if (issue.getId() == null)
		{
			throw new RepositoryException("Issue does not exist");
		}
		
		return createOrUpdate(issue);
	}
}
