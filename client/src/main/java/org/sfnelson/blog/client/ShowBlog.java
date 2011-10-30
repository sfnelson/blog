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
import org.sfnelson.blog.client.editors.PostEditor;
import org.sfnelson.blog.client.events.CreateEntryEvent;
import org.sfnelson.blog.client.events.EntrySelectionEvent;
import org.sfnelson.blog.client.places.EntryPlace;
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.util.RFListManager;
import org.sfnelson.blog.client.views.BlogView;

import java.util.List;
import java.util.Map;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class ShowBlog extends AbstractActivity
		implements ActivityMapper, EntrySelectionEvent.Handler, CreateEntryEvent.Handler {

	private final Provider<EntryRequest> request;
	private final Provider<PostEditor> editor;

	private final BlogView view;
	private final PlaceController pc;

	private EntryPlace place;
	private EntityProxyId<PostProxy> selection = null;

	private RFListManager<PostProxy, PostEditor> list;

	@Inject
	ShowBlog(BlogView view, Provider<EntryRequest> request, Provider<PostEditor> editor, PlaceController pc) {
		this.view = view;
		this.request = request;
		this.pc = pc;
		this.editor = editor;
	}

	private void setPlace(EntryPlace place) {
		this.place = place;

		if (list == null) {
			// continue;
		}
		else if (place == null) {
			// continue;
		}
		else if (place.getAction().equals("create")) {
			create();
		}
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		EntrySelectionEvent.register(this, eventBus);
		CreateEntryEvent.register(this, eventBus);

		list = new RFListManager<PostProxy, PostEditor>(PostProxy.class, eventBus) {
			@Override
			protected void add(int position, PostProxy entity) {
				ShowBlog.this.add(position, entity);
			}

			@Override
			protected void remove(EntityProxyId<PostProxy> id) {
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

		setPlace(place);
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof EntryPlace) {
			setPlace((EntryPlace) place);
		}
		else {
			setPlace(null);
		}

		return this;
	}

	@Override
	public void onEventSelection(EntrySelectionEvent event) {
		if (selection != null && selection.equals(event.getEntryId()) && event.getSelected()) {
			return;
		}

		if (selection != null) {
			PostEditor editor = editors.get(selection);
			if (editor != null) {
				if (!editor.doDeselect()) return;
			}
			selection = null;
		}

		if (event.getSelected() && event.getEntryId() != null) {
			PostEditor editor = editors.get(event.getEntryId());
			if (editor != null && editor.doSelect()) {
				selection = event.getEntryId();
			}
		}
	}

	@Override
	public void entryCreated(CreateEntryEvent event) {
		create();
	}

	private void refresh() {
		request.get().getPosts(0, Integer.MAX_VALUE).fire(new Receiver<List<PostProxy>>() {
			@Override
			public void onSuccess(List<PostProxy> response) {
				list.update(response);
			}
		});
	}

	private Map<EntityProxyId<?>, PostEditor> editors = Maps.newHashMap();

	private void add(int position, PostProxy entry) {
		PostEditor editor = this.editor.get();
		editor.init(entry);
		editors.put(entry.stableId(), editor);
		view.addEntry(position, editor.getView());
	}

	private void remove(EntityProxyId<PostProxy> id) {
		if (editors.containsKey(id)) {
			view.removeEntry(editors.get(id).getView());
		}
	}

	private void create() {
		PostEditor editor = this.editor.get();
		PostProxy entry = editor.create();
		list.addCreated(entry);
		editors.put(entry.stableId(), editor);
		view.addEntry(0, editor.getView());
	}
}
