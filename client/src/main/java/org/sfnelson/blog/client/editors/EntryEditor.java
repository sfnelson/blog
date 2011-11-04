package org.sfnelson.blog.client.editors;

import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import org.sfnelson.blog.client.request.ContentProxy;
import org.sfnelson.blog.client.request.EntryProxy;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.views.EntryView;
import org.sfnelson.blog.server.domain.Entry;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public abstract class EntryEditor<T extends EntryProxy, V extends EntryView<T>>
		extends ModalEditor<T, V> implements EntryView.EntryEditor<T> {

	private final EventBus eventBus;
	private final Provider<EntryRequest> request;

	protected EntryEditor(V view, Provider<EntryRequest> request, EventBus eventBus, Class<T> type) {
		super(view, eventBus, type);
		view.setEditor(this);
		this.request = request;
		this.eventBus = eventBus;
	}

	@Override
	protected EntryRequest edit() {
		EntryRequest context = request.get();
		if (getValue() != null) {
			context.updateContent(getValue().getContent());
		}
		context.updateEntry(getValue());
		return context;
	}

	@Override
	public void requestDelete() {
		requestCancel();
		EntryRequest rq = request.get();
		rq.deleteContent(getValue().getContent());
		rq.deleteEntry(getValue()).fire();
	}
}
