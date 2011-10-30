package org.sfnelson.blog.client;

import com.google.common.collect.Maps;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.Receiver;
import org.sfnelson.blog.client.editors.TaskEditor;
import org.sfnelson.blog.client.places.TaskPlace;
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.request.TaskRequest;
import org.sfnelson.blog.client.util.RFListManager;
import org.sfnelson.blog.client.views.TasksView;

import java.util.List;
import java.util.Map;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class ShowTasks extends AbstractActivity implements ActivityMapper {

	private final TasksView view;
	private final Provider<TaskEditor> editor;
	private final Provider<TaskRequest> rf;

	private RFListManager<TaskProxy, TaskEditor> list;

	private TaskPlace current;

	@Inject
	ShowTasks(TasksView view, Provider<TaskRequest> rf, Provider<TaskEditor> editor) {
		this.view = view;
		this.editor = editor;
		this.rf = rf;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof TaskPlace) {
			setPlace((TaskPlace) place);
		}
		else {
			setPlace(null);
		}
		return this;
	}

	private void setPlace(TaskPlace place) {
		this.current = place;
		if (list == null || place == null) return;
		if (current.getAction().equals("create")) {
			create();
		}
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		list = new RFListManager<TaskProxy, TaskEditor>(TaskProxy.class, eventBus) {
			@Override
			protected void add(int position, TaskProxy entity) {
				ShowTasks.this.add(position, entity);
			}

			@Override
			protected void remove(EntityProxyId<TaskProxy> id) {
				ShowTasks.this.remove(id);
			}
		};
		
		refresh();

		Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
			@Override
			public boolean execute() {
				return true;
			}
		}, 60000);

		panel.setWidget(view);

		setPlace(current);
	}

	private void refresh() {
		rf.get().getCurrentTasks(0, Integer.MAX_VALUE).fire(new Receiver<List<TaskProxy>>() {
			@Override
			public void onSuccess(List<TaskProxy> response) {
				list.update(response);
			}
		});
	}

	private Map<EntityProxyId<?>, TaskEditor> editors = Maps.newHashMap();

	private void add(int position, TaskProxy entry) {
		TaskEditor editor = this.editor.get();
		editor.init(entry);
		editors.put(entry.stableId(), editor);
		view.addTask(position, editor.getView());
	}

	private void remove(EntityProxyId<TaskProxy> id) {
		if (editors.containsKey(id)) {
			view.removeTask(editors.get(id).getView());
		}
	}

	private void create() {
		TaskEditor editor = this.editor.get();
		TaskProxy entry = editor.create();
		list.addCreated(entry);
		editors.put(entry.stableId(), editor);
		view.addTask(0, editor.getView());
	}
}
