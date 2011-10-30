package org.sfnelson.blog.client.editors;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.web.bindery.requestfactory.shared.EntityProxy;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public abstract class HasValueEditor<T extends EntityProxy, View> implements ValueAwareEditor<T> {

	private final View view;

	private T lastValue;
	private T value;

	protected HasValueEditor(View view) {
		this.view = view;
	}

	@Editor.Ignore
	public View getView() {
		return view;
	}

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
