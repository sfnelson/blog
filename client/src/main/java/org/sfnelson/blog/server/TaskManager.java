package org.sfnelson.blog.server;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.mongo.Database;
import org.sfnelson.blog.server.domain.Task;
import org.sfnelson.blog.server.service.TaskService;

import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class TaskManager implements TaskService {

	private final Database database;

	@Inject
	TaskManager(Database database) {
		this.database = database;
	}

	@Override
	public List<Task> getCurrentTasks(int start, int limit) {
		List<Task> tasks = Lists.newArrayList();
		int count = 0;
		for (DBObject obj : database.findActiveTasks().skip(start)) {
			tasks.add(new Task(obj));
			if (++count >= limit) break;
		}
		return tasks;
	}

	@Override
	public void createTask(Task task) {
		database.persist(task);
	}

	@Override
	public void updateTask(Task task) {
		database.update(task);
	}
}
