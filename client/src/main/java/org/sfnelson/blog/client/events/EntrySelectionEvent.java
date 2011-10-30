package org.sfnelson.blog.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import org.sfnelson.blog.client.request.PostProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
public class EntrySelectionEvent extends Event<EntrySelectionEvent.Handler> {

	private static final Event.Type<Handler> TYPE = new Event.Type<Handler>();

	public interface Handler extends EventHandler {
		void onEventSelection(EntrySelectionEvent event);
	}

	public static HandlerRegistration register(Handler handler, EventBus eventBus) {
		return eventBus.addHandler(TYPE, handler);
	}

	private final PostProxy entry;
	private final boolean selected;

	public EntrySelectionEvent(PostProxy entry, boolean selected) {
		this.entry = entry;
		this.selected = selected;
	}

	public EntityProxyId<PostProxy> getEntryId() {
		return entry.stableId();
	}

	public boolean getSelected() {
		return selected;
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onEventSelection(this);
	}
}
