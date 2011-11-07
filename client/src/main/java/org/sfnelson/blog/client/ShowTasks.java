package org.sfnelson.blog.client;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.EntityProxyChange;
import com.google.web.bindery.requestfactory.shared.Receiver;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.events.CreateTaskEvent;
import org.sfnelson.blog.client.events.EditorSelectionEvent;
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.request.TaskRequest;
import org.sfnelson.blog.client.views.TasksView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class ShowTasks extends AbstractActivity
		implements ActivityMapper, CreateTaskEvent.Handler, EntityProxyChange.Handler {

	private final TasksView view;
	private final Provider<TaskRequest> rf;
	private final EventBus eventBus;

	@Inject
	ShowTasks(TasksView view, Provider<TaskRequest> rf, EventBus eventBus) {
		this.view = view;
		this.rf = rf;
		this.eventBus = eventBus;
	}

	@Override
	public Activity getActivity(Place place) {
		return this;
	}

	public EditorMapper getEditorMapper() {
		return view.getList();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.getList().clear();

		CreateTaskEvent.register(this, eventBus);
		EntityProxyChange.registerForProxyType(eventBus, TaskProxy.class, this);

		panel.setWidget(view);

		rf.get().getCurrentTasks(0, Integer.MAX_VALUE)
				.fire(new Receiver<List<TaskProxy>>() {
					@Override
					public void onSuccess(List<TaskProxy> response) {
						view.getList().addAll(response);
					}
				});
	}

	@Override
	public void onTaskCreated(CreateTaskEvent event) {
		final TaskProxy task = view.getList().create(0, TaskProxy.class);
		Scheduler.get().scheduleDeferred(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new EditorSelectionEvent<TaskProxy>(task, EditorSelectionEvent.Type.SELECT));
			}
		});
	}

	@Override
	public void onProxyChange(EntityProxyChange event) {
		switch (event.getWriteOperation()) {
			case UPDATE:
				rf.get().find(event.getProxyId()).fire(new Receiver<TaskProxy>() {
					@Override
					public void onSuccess(TaskProxy task) {
						if (task.getCompleted() != null || task.getAbandoned() != null) {
							view.getList().remove(task);
						}
					}
				});
				break;
			case DELETE:
				view.getList().remove(event.getProxyId());
				break;
		}
	}
}
