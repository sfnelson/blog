package org.sfnelson.blog.client.editors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import org.sfnelson.blog.client.events.CreateTaskUpdateEvent;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.request.TaskRequest;
import org.sfnelson.blog.client.views.TaskView;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 30/10/11
 */
public class TaskEditor extends ModalEditor<TaskProxy, TaskView> implements TaskView.Editor {

	interface Driver extends RequestFactoryEditorDriver<TaskProxy, TaskEditor> {};

	private final Driver driver;
	private final Provider<TaskRequest> request;
	private final EventBus eventBus;

	@Inject
	TaskEditor(TaskView view, RequestFactory rf, EventBus eventBus, Provider<TaskRequest> request) {
		super(view, eventBus, TaskProxy.class);
		this.eventBus = eventBus;
		view.setEditor(this);

		this.request = request;

		driver = GWT.create(Driver.class);
		driver.initialize(rf, this);
	}

	Editor<String> title() {
		return getView().getTitleEditor();
	}

	@Override
	protected RequestFactoryEditorDriver<TaskProxy, ? extends ModalEditor<TaskProxy, ?>> getDriver() {
		return driver;
	}

	@Override
	protected TaskRequest edit() {
		TaskRequest context = request.get();
		context.updateTask(getValue());
		return context;
	}

	public TaskProxy create() {
		TaskRequest context = request.get();
		TaskProxy value = context.create(TaskProxy.class);
		context.createTask(value);
		value.setTitle("");
		create(value, context);
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				requestSelect();
				getView().focus();
			}
		});
		return value;
	}

	@Override
	public void markComplete() {
		eventBus.fireEvent(new CreateTaskUpdateEvent(getValue(), TaskUpdateType.COMPLETED));
	}

	@Override
	public void markProgress() {
		eventBus.fireEvent(new CreateTaskUpdateEvent(getValue(), TaskUpdateType.PROGRESS));
	}

	@Override
	public void markAbandoned() {
		eventBus.fireEvent(new CreateTaskUpdateEvent(getValue(), TaskUpdateType.ABANDONED));
	}
}
