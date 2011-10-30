package org.sfnelson.blog.client.views;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public interface TasksView extends IsWidget {

	void addTask(int position, TaskView task);
	void removeTask(TaskView task);

}
