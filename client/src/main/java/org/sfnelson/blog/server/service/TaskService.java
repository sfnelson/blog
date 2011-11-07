package org.sfnelson.blog.server.service;

import java.util.List;

import org.sfnelson.blog.server.domain.Task;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface TaskService {
	List<Task> getCurrentTasks(int start, int limit);

	void createTask(Task task);

	void updateTask(Task task);
}
