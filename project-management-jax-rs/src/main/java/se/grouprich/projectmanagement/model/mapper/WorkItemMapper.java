package se.grouprich.projectmanagement.model.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import se.grouprich.projectmanagement.Loader;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.model.WorkItemData;
import se.grouprich.projectmanagement.service.WorkItemService;

import java.util.ArrayList;
import java.util.List;

public final class WorkItemMapper
{
	private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	private final MapperFacade mapper = mapperFactory.getMapperFacade();
	private static final WorkItemService teamService = Loader.getBean(WorkItemService.class);

	public WorkItemMapper()
	{
		mapperFactory.classMap(WorkItem.class, WorkItemData.class).fieldBToA("id", "id").exclude("status").byDefault().register();
	}

	public WorkItemData convertWorkItemToWorkItemData(final WorkItem workItem)
	{
		WorkItemData workItemData = mapper.map(workItem, WorkItemData.class);
		return workItemData;
	}

	public WorkItem convertWorkItemDataToWorkItem(final WorkItemData workItemData)
	{
		WorkItem workItem = mapper.map(workItemData, WorkItem.class);
		workItem.setStatus(workItemData.getStatus().toString());

		return workItem;
	}

	public List<WorkItem> convertList(final List<WorkItemData> workItemDataList)
	{
		List<WorkItem> workItems = new ArrayList<>();
		workItemDataList.forEach(workItemData -> workItems.add(convertWorkItemDataToWorkItem(workItemData)));

		return workItems;
	}
}
