package org.sfnelson.blog.client.views;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.user.client.ui.IsWidget;

import org.sfnelson.blog.client.editors.RootEditor;
import org.sfnelson.blog.client.request.TaskProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 30/10/11
 */
public interface TaskView extends ModalEditorView, IsWidget, IsEditor<RootEditor<TaskProxy>> {
	interface TaskEditor extends ModalEditorView.Editor<TaskProxy> {
		@Ignore
		TaskView asWidget();
	}
}
