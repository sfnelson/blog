package org.sfnelson.blog.client.widgets;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

import org.sfnelson.blog.shared.domain.TaskUpdateType;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskUpdateTypeWidget extends Composite implements LeafValueEditor<TaskUpdateType> {

	private final Label value;
	private TaskUpdateType type;

	public TaskUpdateTypeWidget() {
		value = new Label();
		initWidget(value);
	}

	@Override
	public void setValue(TaskUpdateType type) {
		this.type = type;
		if (type != null) {
			this.value.setText(type.name());
		} else {
			this.value.setText("");
		}
	}

	@Override
	public TaskUpdateType getValue() {
		return type;
	}
}
