package org.sfnelson.blog.client.widgets;

import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.HandlerRegistration;

import org.sfnelson.blog.client.request.TaskProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskNameWidget extends ComplexPanel implements ValueAwareEditor<TaskProxy> {

	private EditorDelegate<TaskProxy> delegate;
	private HandlerRegistration registration;

	Label title;

	public TaskNameWidget() {
		title = new InlineLabel();
		setElement(DOM.createElement("H1"));
		add(title, getElement());
	}

	@Override
	public void setDelegate(EditorDelegate<TaskProxy> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void flush() {
		// nothing to do.
	}

	@Override
	public void onPropertyChange(String... paths) {
		// nothing to do.
	}

	@Override
	public void setValue(TaskProxy value) {
		if (registration == null && delegate != null) {
			registration = delegate.subscribe();
		}
	}
}
