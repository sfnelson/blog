package org.sfnelson.blog.client.editors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyChange;
import com.google.web.bindery.requestfactory.shared.WriteOperation;
import org.sfnelson.blog.client.events.EntrySelectionEvent;
import org.sfnelson.blog.client.places.ManagerPlace;
import org.sfnelson.blog.client.request.EntryProxy;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.views.EntryView;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public abstract class EntityEditor<T extends EntityProxy, View> implements ValueAwareEditor<T> {

	private final View view;

	private T lastValue;
	private T value;

	protected EntityEditor(View view) {
		this.view = view;
	}

	@Editor.Ignore
	public View getView() {
		return view;
	}

	public abstract T create();

	public abstract void init(T value);

	@Override
	public void flush() {
		// nothing to do.
	}

	@Override
	public void onPropertyChange(String... paths) {
		// nothing to do.
	}

	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		// keep an up-to-date version of the proxy.
		this.lastValue = this.value;
		this.value = value;
	}

	protected void reset() {
		if (lastValue != null) {
			value = lastValue;
		}
	}

	@Override
	public void setDelegate(EditorDelegate<T> entryProxyEditorDelegate) {
		// nothing to do.
	}
}
