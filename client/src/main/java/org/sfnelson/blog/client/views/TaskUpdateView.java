package org.sfnelson.blog.client.views;

import com.google.gwt.editor.client.Editor;
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.request.TaskUpdateProxy;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface TaskUpdateView extends EntryView<TaskUpdateProxy> {
	com.google.gwt.editor.client.Editor<String> getContentEditor();
	com.google.gwt.editor.client.Editor<String> getTaskNameEditor();
	com.google.gwt.editor.client.Editor<TaskUpdateType> getTypeEditor();
	void focus();
}
