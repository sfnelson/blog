package org.sfnelson.blog.client.editors;

import com.google.web.bindery.event.shared.EventBus;

import com.google.inject.Provider;
import org.sfnelson.blog.client.request.EntryProxy;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.views.EntryView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public abstract class EntryEditor<T extends EntryProxy, V extends EntryView<T>>
		extends ModalEditor<T, V> implements EntryView.EntryEditor<T> {

	private final Provider<EntryRequest> request;

	protected EntryEditor(V view, Provider<EntryRequest> request, EventBus eventBus, Class<T> type) {
		super(view, eventBus, type);
		this.request = request;
	}

	protected EntryRequest getRequest() {
		return request.get();
	}

	@Override
	public void requestDelete() {
		requestCancel();
		EntryRequest rq = request.get();
		rq.deleteContent(getValue().getContent());
		rq.deleteEntry(getValue()).fire();
	}
}
