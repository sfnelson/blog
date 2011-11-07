package org.sfnelson.blog.client.events;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class CreatePostEvent extends Event<CreatePostEvent.Handler> {

	public interface Handler {
		void onEntryCreated(CreatePostEvent event);
	}

	private static final Event.Type<Handler> TYPE = new Event.Type<Handler>();

	public static HandlerRegistration register(Handler handler, EventBus eventBus) {
		return eventBus.addHandler(TYPE, handler);
	}

	public CreatePostEvent() {
	}

	@Override
	public Event.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onEntryCreated(this);
	}
}
