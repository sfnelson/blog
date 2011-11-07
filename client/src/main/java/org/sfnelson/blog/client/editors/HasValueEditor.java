package org.sfnelson.blog.client.editors;

import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.requestfactory.shared.EntityProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public abstract class HasValueEditor<T extends EntityProxy> implements ValueAwareEditor<T> {

	private EditorDelegate<T> delegate;
	private HandlerRegistration registration;

	private T lastValue;
	private T value;

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

		if (delegate != null && registration == null) {
			registration = delegate.subscribe();
		}
	}

	protected void reset() {
		if (lastValue != null) {
			value = lastValue;
		}
	}

	@Override
	public void setDelegate(EditorDelegate<T> delegate) {
		this.delegate = delegate;

		if (value != null && registration == null) {
			registration = delegate.subscribe();
		}
	}
}
