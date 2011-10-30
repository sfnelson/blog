package org.sfnelson.blog.client.views;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import org.sfnelson.blog.client.request.TaskProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 30/10/11
 */
public interface TaskView extends ModalEditorView<TaskProxy>, IsWidget {

	com.google.gwt.editor.client.Editor<String> getTitleEditor();

	void setEditor(Editor editor);
	void focus();

	interface Editor extends ModalEditorView.Editor<TaskProxy> {
		void markComplete();
		void markProgress();
		void markAbandoned();
	}
}
