package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.sfnelson.blog.client.views.TaskView;
import org.sfnelson.blog.client.views.TasksView;
import org.sfnelson.blog.client.request.TaskProxy;

import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class TaskList extends Composite implements TasksView {
	interface Binder extends UiBinder<FlowPanel, TaskList> {}

	@UiField FlowPanel tasks;

	public TaskList() {
		initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));
	}

	@Override
	public void addTask(int position, TaskView task) {
		tasks.insert(task, position);
	}

	@Override
	public void removeTask(TaskView task) {
		tasks.remove(task);
	}
}