package org.sfnelson.blog.client.events;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class CreateTaskEvent extends Event<CreateTaskEvent.Handler> {

	public interface Handler {
		void onTaskCreated(CreateTaskEvent event);
	}

	private static final Event.Type<Handler> TYPE = new Event.Type<Handler>();

	public static HandlerRegistration register(Handler handler, EventBus eventBus) {
		return eventBus.addHandler(TYPE, handler);
	}

	public CreateTaskEvent() {
	}

	@Override
	public Event.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onTaskCreated(this);
	}
}
