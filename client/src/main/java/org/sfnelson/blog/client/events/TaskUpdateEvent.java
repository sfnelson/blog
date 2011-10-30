package org.sfnelson.blog.client.events;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.sfnelson.blog.client.request.TaskProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskUpdateEvent extends Event<TaskUpdateEvent.Handler> {

	public static HandlerRegistration register(Handler handler, EventBus eventBus) {
		return eventBus.addHandler(TYPE, handler);
	}

	public interface Handler {
		void createTaskUpdate(TaskUpdateEvent event);
	}

	private static final Type<Handler> TYPE = new Type<Handler>();

	private final TaskProxy task;

	public TaskUpdateEvent(TaskProxy task) {
		this.task = task;
	}

	public TaskProxy getTask() {
		return task;
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createTaskUpdate(this);
	}
}
