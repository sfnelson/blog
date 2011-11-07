package org.sfnelson.blog.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
public class EditorSelectionEvent<T extends EntityProxy> extends Event<EditorSelectionEvent.Handler<T>> {

	private static final Event.Type<Handler<?>> TYPE = new Event.Type<Handler<?>>();

	public enum Type {
		SELECT,
		PREVIOUS,
		NEXT,
		BLUR;
	}

	public interface Handler<T extends EntityProxy> extends EventHandler {
		void onSelectionEvent(EditorSelectionEvent<T> event);
	}

	public static <T extends EntityProxy> HandlerRegistration register(Handler<T> handler, EventBus eventBus) {
		return eventBus.addHandler(TYPE, handler);
	}

	private final T value;
	private final Type type;

	public EditorSelectionEvent(T value, Type type) {
		this.value = value;
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public EntityProxyId<T> getEntryId() {
		return (EntityProxyId<T>) value.stableId();
	}

	public Type getType() {
		return type;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Event.Type<Handler<T>> getAssociatedType() {
		return (Event.Type) TYPE;
	}

	@Override
	protected void dispatch(Handler<T> handler) {
		handler.onSelectionEvent(this);
	}
}
