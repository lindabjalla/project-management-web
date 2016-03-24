package se.grouprich.projectmanagement;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.grouprich.projectmanagement.model.UserData;
import se.grouprich.projectmanagement.service.UserService;
import se.grouprich.projectmanagement.service.WorkItemService;

public final class Main
{
	public static void main(String[] args)
	{
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
		{
			context.scan("se.grouprich.projectmanagement");
			context.refresh();
			UserService userService = context.getBean(UserService.class);
			WorkItemService workItemService = context.getBean(WorkItemService.class);
			
			userService.createOrUpdate(new UserData("SumireSuzukiNeko", "33TTu?", "Sumire", "Suzuki"));
		}
	}
}
