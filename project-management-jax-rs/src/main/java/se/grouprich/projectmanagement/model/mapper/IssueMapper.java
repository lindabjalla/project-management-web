package se.grouprich.projectmanagement.model.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.model.Issue;
import se.grouprich.projectmanagement.model.IssueData;
import se.grouprich.projectmanagement.service.IssueService;

public class IssueMapper 
{
	private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	private final MapperFacade mapperFacade = mapperFactory.getMapperFacade();
	private final IssueService issueService = Loader.getBean(IssueService.class);
	
	public IssueMapper()
	{
		mapperFactory.classMap(Issue.class,  IssueData.class).fieldBToA("id", "id");
	}
	
	public IssueData convertIssueToIssueData(final Issue issue)
	{
		IssueData issueData = mapperFacade.map(issue, IssueData.class);
		return issueData;
	}
	
	public Issue convertIssueDataToIssue(final IssueData issueData)
	{
		Issue issue = mapperFacade.map(issueData, Issue.class);
		return issue;
	}
	
	public IssueData updateIssueData(final Issue issue, final IssueData issueData)
	{
		issueData.setDescription(issue.getDescription());
		return issueData;		
	}
}
