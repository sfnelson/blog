package org.sfnelson.blog.client.views;

import org.sfnelson.blog.client.editors.ContentEditor;
import org.sfnelson.blog.client.request.ContentProxy;
import org.sfnelson.blog.client.request.UpdateProxy;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface UpdateView extends EntryView<UpdateProxy> {
	ContentEditor getContentEditor();
	com.google.gwt.editor.client.Editor<String> getTaskNameEditor();
	com.google.gwt.editor.client.Editor<TaskUpdateType> getTypeEditor();
	void focus();
}
