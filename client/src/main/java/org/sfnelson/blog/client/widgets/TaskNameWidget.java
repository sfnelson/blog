package org.sfnelson.blog.client.widgets;

import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import org.sfnelson.blog.client.request.TaskProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskNameWidget extends ComplexPanel implements ValueAwareEditor<String> {

	public TaskNameWidget() {
		setElement(DOM.createElement("H1"));
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
	public void setValue(String value) {
		if (value != null) {
			getElement().setInnerText(value);
		}
	}

	@Override
	public void setDelegate(EditorDelegate<String> taskEditorDelegate) {
		// nothing to do.
	}
}
