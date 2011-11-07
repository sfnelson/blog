package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.web.bindery.event.shared.EventBus;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.editors.EditorSource;
import org.sfnelson.blog.client.editors.EntryEditor;
import org.sfnelson.blog.client.editors.RootEditor;
import org.sfnelson.blog.client.events.EditorSelectionEvent;
import org.sfnelson.blog.client.request.EntryProxy;
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.request.UpdateProxy;
import org.sfnelson.blog.client.util.EditorList;
import org.sfnelson.blog.client.views.BlogView;
import org.sfnelson.blog.client.views.EntryView;
import org.sfnelson.blog.client.views.PostView;
import org.sfnelson.blog.client.views.UpdateView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
public class EntryList extends Composite implements BlogView, KeyPressHandler {
	interface Binder extends UiBinder<FlowPanel, EntryList> {
	}

	private class Source implements EditorSource<EntryProxy> {
		@Override
		public <X extends EntryProxy> RootEditor<X> create(int index, Class<X> type) {
			EntryView<X> widget = create(type);
			entries.insert(widget, index);
			return widget.asEditor();
		}

		@Override
		public void dispose(Object o) {
			if (o instanceof EntryEditor) {
				entries.remove(((EntryView.EntryEditor<?>) o).asWidget());
			}
		}

		@SuppressWarnings("unchecked")
		private <X extends EntryProxy> EntryView<X> create(Class<X> type) {
			if (type.getName().startsWith(PostProxy.class.getName())) return (EntryView<X>) posts.get();
			if (type.getName().startsWith(UpdateProxy.class.getName())) return (EntryView<X>) updates.get();
			throw new ClassCastException("unexpected class " + type);
		}
	}

	private final EventBus eventBus;
	private final Provider<PostView> posts;
	private final Provider<UpdateView> updates;
	private final EditorList<EntryProxy> list;

	@UiField
	FlowPanel entries;

	@Inject
	EntryList(EventBus eventBus, Provider<PostView> posts, Provider<UpdateView> updates) {
		this.eventBus = eventBus;
		this.posts = posts;
		this.updates = updates;
		this.list = new EditorList<EntryProxy>(new Source());

		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));

		addDomHandler(this, KeyPressEvent.getType());
	}

	@Override
	public EditorList<EntryProxy> getList() {
		return list;
	}

	public void onKeyPress(KeyPressEvent ev) {
		switch (ev.getCharCode()) {
			case 'j':
				eventBus.fireEvent(new EditorSelectionEvent(null, EditorSelectionEvent.Type.NEXT));
				break;
			case 'k':
				eventBus.fireEvent(new EditorSelectionEvent(null, EditorSelectionEvent.Type.PREVIOUS));
				break;
		}
	}
}