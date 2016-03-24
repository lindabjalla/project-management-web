package se.grouprich.projectmanagement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.grouprich.projectmanagement.service.UserService;
import se.grouprich.projectmanagement.service.WorkItemService;

@ApplicationPath("/*")
public final class Loader extends Application
{
	private static AnnotationConfigApplicationContext context;

	public static <T> T getBean(Class<T> type)
	{
		return context.getBean(type);
	}

	@PostConstruct
	public void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("se.grouprich.projectmanagement");
		context.refresh();
	}

	@PreDestroy
	public void destroy()
	{
		context.destroy();
	}
}
