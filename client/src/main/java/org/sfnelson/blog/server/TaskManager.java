package org.sfnelson.blog.server;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.DBObject;
import org.sfnelson.blog.server.mongo.Database;
import org.sfnelson.blog.server.domain.Task;
import org.sfnelson.blog.server.security.RequiresLogin;
import org.sfnelson.blog.server.service.TaskService;

import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class TaskManager implements TaskService {

	private final Database database;
	private final Provider<Task> tasks;

	@Inject
	TaskManager(Database database, Provider<Task> tasks) {
		this.database = database;
		this.tasks = tasks;
	}

	@Override
	public List<Task> getCurrentTasks(int start, int limit) {
		List<Task> tasks = Lists.newArrayList();
		int count = 0;
		for (DBObject obj : database.findActiveTasks().skip(start)) {
			tasks.add(this.tasks.get().init(obj));
			if (++count >= limit) break;
		}
		return tasks;
	}

	@Override
	@RequiresLogin
	public void createTask(Task task) {
		database.persist(task);
		if (task.getContent() != null) {
			database.update(task.getContent());
		}
	}

	@Override
	@RequiresLogin
	public void updateTask(Task task) {
		database.update(task);
		if (task.getContent() != null) {
			database.update(task.getContent());
		}
	}
}
