package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import org.sfnelson.blog.server.ServiceLocator;
import org.sfnelson.blog.server.TaskManager;
import org.sfnelson.blog.server.service.TaskService;

import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
@Service(value = TaskService.class, locator = ServiceLocator.class)
public interface TaskRequest extends RequestContext {
	Request<List<TaskProxy>> getCurrentTasks(int start, int limit);
	Request<Void> createTask(TaskProxy task);
	Request<Void> updateTask(TaskProxy task);
}
