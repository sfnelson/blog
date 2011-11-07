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
import org.sfnelson.blog.client.editors.RootEditor;
import org.sfnelson.blog.client.events.CreatePostEvent;
import org.sfnelson.blog.client.events.CreateUpdateEvent;
import org.sfnelson.blog.client.events.EditorSelectionEvent;
import org.sfnelson.blog.client.request.EntryProxy;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.request.UpdateProxy;
import org.sfnelson.blog.client.views.BlogView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 5/11/11
 */
public class ShowBlog extends AbstractActivity
		implements ActivityMapper, CreatePostEvent.Handler, CreateUpdateEvent.Handler {

	private final BlogView view;
	private final EventBus eventBus;
	private final Provider<EntryRequest> request;

	@Inject
	ShowBlog(BlogView view, EventBus eventBus, Provider<EntryRequest> request) {
		this.view = view;
		this.eventBus = eventBus;
		this.request = request;
	}

	@Override
	public Activity getActivity(Place place) {
		return this;
	}

	public EditorMapper getEditorMapper() {
		return view.getList();
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		view.getList().clear();

		CreatePostEvent.register(this, eventBus);
		CreateUpdateEvent.register(this, eventBus);

		EntityProxyChange.registerForProxyType(eventBus, PostProxy.class,
				new EntityProxyChange.Handler<PostProxy>() {
					@Override
					public void onProxyChange(EntityProxyChange<PostProxy> event) {
						ShowBlog.this.onProxyChange(event);
					}
				});
		EntityProxyChange.registerForProxyType(eventBus, UpdateProxy.class,
				new EntityProxyChange.Handler<UpdateProxy>() {
					@Override
					public void onProxyChange(EntityProxyChange<UpdateProxy> event) {
						ShowBlog.this.onProxyChange(event);
					}
				});

		panel.setWidget(view);

		request.get().getEntries(0, 10).with("task", "progress", "content")
				.fire(new Receiver<List<EntryProxy>>() {
					@Override
					public void onSuccess(List<EntryProxy> response) {
						view.getList().addAll(response);
					}
				});
	}

	@Override
	public void onEntryCreated(CreatePostEvent event) {
		final PostProxy post = view.getList().create(0, PostProxy.class);
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				eventBus.fireEvent(new EditorSelectionEvent(post, EditorSelectionEvent.Type.SELECT));
			}
		});
	}

	@Override
	public void onTaskUpdateCreated(final CreateUpdateEvent event) {
		final UpdateProxy update = view.getList().create(0, UpdateProxy.class,
				new RootEditor.Initializer<UpdateProxy>() {
					@Override
					public void initialize(UpdateProxy value) {
						value.setTask(event.getTask());
						value.setType(event.getType());
					}
				});
		Scheduler.get().scheduleDeferred(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new EditorSelectionEvent(update, EditorSelectionEvent.Type.SELECT));
			}
		});
	}

	void onProxyChange(EntityProxyChange<? extends EntryProxy> event) {
		switch (event.getWriteOperation()) {
			case DELETE:
				view.getList().remove(event.getProxyId());
				break;
		}
	}
}
