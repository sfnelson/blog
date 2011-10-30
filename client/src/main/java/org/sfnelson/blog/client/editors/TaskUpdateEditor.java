package org.sfnelson.blog.client.editors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.Editor;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import org.sfnelson.blog.client.request.*;
import org.sfnelson.blog.client.views.TaskUpdateView;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskUpdateEditor extends EntryEditor<TaskUpdateProxy, TaskUpdateView> {

	interface Driver extends RequestFactoryEditorDriver<TaskUpdateProxy, TaskUpdateEditor> {}

	private final Driver driver = GWT.create(Driver.class);

	@Inject
	TaskUpdateEditor(TaskUpdateView view, Provider<EntryRequest> request, EventBus eventBus, RequestFactory rf) {
		super(view, request, eventBus, TaskUpdateProxy.class);
		driver.initialize(rf, this);
	}

	Editor<String> content() {
		return getView().getContentEditor();
	}

	@Path("task.title")
	Editor<String> task() {
		return getView().getTaskNameEditor();
	}

	Editor<TaskUpdateType> type() {
		return getView().getTypeEditor();
	}

	@Override
	protected RequestFactoryEditorDriver<TaskUpdateProxy, ? extends EntryEditor<TaskUpdateProxy, ?>> getDriver() {
		return driver;
	}

	public TaskUpdateProxy create(TaskProxy task, TaskUpdateType type) {
		EntryRequest context = edit();
		TaskUpdateProxy value = context.create(TaskUpdateProxy.class);
		value.setTask(task);
		value.setPosted(new Date());
		value.setType(type);
		value.setContent("<p>Comment</p>");
		context.create(value);
		Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
			@Override
			public boolean execute() {
				requestSelect();
				getView().focus();
				return false;
			}
		}, 50);
		return create(value, context);
	}
}
