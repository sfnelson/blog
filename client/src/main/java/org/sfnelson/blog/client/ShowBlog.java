package org.sfnelson.blog.client;

import com.google.common.collect.Maps;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.requestfactory.shared.*;
import org.sfnelson.blog.client.editors.EntryEditor;
import org.sfnelson.blog.client.editors.PostEditor;
import org.sfnelson.blog.client.editors.TaskUpdateEditor;
import org.sfnelson.blog.client.events.CreateEntryEvent;
import org.sfnelson.blog.client.events.CreateTaskUpdateEvent;
import org.sfnelson.blog.client.events.EditorSelectionEvent;
import org.sfnelson.blog.client.places.EntryPlace;
import org.sfnelson.blog.client.request.*;
import org.sfnelson.blog.client.util.RFListManager;
import org.sfnelson.blog.client.views.BlogView;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

import java.util.List;
import java.util.Map;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class ShowBlog extends AbstractActivity implements ActivityMapper, EditorSelectionEvent.Handler {

	private final Provider<EntryRequest> request;
	private final Provider<PostEditor> posts;
	private final Provider<TaskUpdateEditor> updates;

	private final BlogView view;
	private final PlaceController pc;

	private EntryPlace place;
	private EntityProxyId<EntryProxy> selection = null;

	private RFListManager<EntryProxy, EntryEditor<EntryProxy, ?>> list;

	@Inject
	ShowBlog(BlogView view, Provider<EntryRequest> request, Provider<PostEditor> posts,
			 Provider<TaskUpdateEditor> updates, PlaceController pc) {
		this.view = view;
		this.request = request;
		this.pc = pc;
		this.posts = posts;
		this.updates = updates;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof EntryPlace) {
			this.place = (EntryPlace) place;
		}
		else {
			this.place = null;
		}

		return this;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		EditorSelectionEvent.register(this, eventBus);

		CreateEntryEvent.register(new CreateEntryEvent.Handler() {
			@Override
			public void onEntryCreated(CreateEntryEvent event) {
				createPost();
			}
		}, eventBus);
		CreateTaskUpdateEvent.register(new CreateTaskUpdateEvent.Handler() {
			@Override
			public void onTaskUpdateCreated(CreateTaskUpdateEvent event) {
				createTaskUpdate(event.getTask(), event.getType());
			}
		}, eventBus);

		list = new RFListManager<EntryProxy, EntryEditor<EntryProxy, ?>>(eventBus,
				PostProxy.class, TaskUpdateProxy.class) {
			@Override
			protected void add(int position, EntryProxy entity) {
				ShowBlog.this.add(position, entity);
			}

			@Override
			protected void remove(EntityProxyId<? extends EntryProxy> id) {
				ShowBlog.this.remove(id);
			}
		};

		refresh();

		Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
			@Override
			public boolean execute() {
				refresh();
				return true;
			}
		}, 60000);


		panel.setWidget(view);
	}

	@Override
	public void onEventSelection(EditorSelectionEvent event) {
		if (selection != null && selection.equals(event.getEntryId()) && event.getSelected()) {
			return;
		}

		if (selection != null) {
			EntryEditor<?, ?> editor = editors.get(selection);
			if (editor != null) {
				if (!editor.deselect()) return;
			}
			selection = null;
		}

		if (event.getSelected() && event.getEntryId() != null) {
			EntryEditor<?, ?> editor = editors.get(event.getEntryId());
			if (editor != null && editor.select()) {
				selection = event.getEntryId();
			}
		}
	}

	private void refresh() {
		request.get().getEntries(0, Integer.MAX_VALUE).with("task", "progress")
		.fire(new Receiver<List<EntryProxy>>() {
			@Override
			public void onSuccess(List<EntryProxy> response) {
				list.update(response);
			}
		});
	}

	private Map<EntityProxyId<?>, EntryEditor<?, ?>> editors = Maps.newHashMap();

	private void add(int position, EntryProxy entry) {
		EntryEditor<?, ?> editor;
		if (entry instanceof PostProxy) {
			PostEditor e = this.posts.get();
			e.init((PostProxy) entry);
			editor = e;
		}
		else {
			TaskUpdateEditor e = this.updates.get();
			e.init((TaskUpdateProxy) entry);
			editor = e;
		}
		editors.put(entry.stableId(), editor);
		view.addEntry(position, editor.getView());
	}

	private void remove(EntityProxyId<? extends EntryProxy> id) {
		if (editors.containsKey(id)) {
			view.removeEntry(editors.get(id).getView());
		}
	}

	private void createPost() {
		PostEditor editor = this.posts.get();
		PostProxy entry = editor.create();
		list.addCreated(entry);
		editors.put(entry.stableId(), editor);
		view.addEntry(0, editor.getView());
	}

	private void createTaskUpdate(TaskProxy task, TaskUpdateType type) {
		TaskUpdateEditor editor = this.updates.get();
		TaskUpdateProxy entry = editor.create(task, type);
		list.addCreated(entry);
		editors.put(entry.stableId(), editor);
		view.addEntry(0, editor.getView());
	}
}
