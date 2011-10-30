package org.sfnelson.blog.client.widgets;

import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskUpdateTypeWidget extends Composite implements ValueAwareEditor<TaskUpdateType> {

	private final Label value;

	public TaskUpdateTypeWidget() {
		value = new Label();
		initWidget(value);
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
	public void setValue(TaskUpdateType value) {
		this.value.setText(value.name());
	}

	@Override
	public void setDelegate(EditorDelegate<TaskUpdateType> taskUpdateTypeEditorDelegate) {
		// nothing to do.
	}
}
