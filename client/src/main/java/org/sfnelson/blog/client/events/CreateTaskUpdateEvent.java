package org.sfnelson.blog.client.events;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class CreateTaskUpdateEvent extends Event<CreateTaskUpdateEvent.Handler> {

	public interface Handler {
		void onTaskUpdateCreated(CreateTaskUpdateEvent event);
	}

	private static final Event.Type<Handler> TYPE = new Event.Type<Handler>();

	public static HandlerRegistration register(Handler handler, EventBus eventBus) {
		return eventBus.addHandler(TYPE, handler);
	}

	private final TaskProxy task;
	private final TaskUpdateType type;

	public CreateTaskUpdateEvent(TaskProxy task, TaskUpdateType type) {
		this.task = task;
		this.type = type;
	}

	public TaskProxy getTask() {
		return task;
	}

	public TaskUpdateType getType() {
		return type;
	}

	@Override
	public Event.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onTaskUpdateCreated(this);
	}
}
