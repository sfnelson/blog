package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.views.BlogView;
import org.sfnelson.blog.client.views.EntryView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
public class EntryList extends Composite implements BlogView {
	interface Binder extends UiBinder<FlowPanel, EntryList> {}

	private final Provider<EntryView> editorSource;

	@UiField FlowPanel entries;

	@Inject
	EntryList(Provider<EntryView> editorSource) {
		this.editorSource = editorSource;

		initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));
	}

	@Override
	public void addEntry(int position, EntryView view) {
		entries.insert(view, position);
	}

	@Override
	public void removeEntry(EntryView view) {
		entries.remove(view);
	}
}