package org.sfnelson.blog.client.views;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import org.sfnelson.blog.client.request.TaskProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 30/10/11
 */
public interface TaskView extends IsWidget {

	void setPresenter(Presenter presenter);
	Editor<String> getTitleEditor();

	interface Presenter extends Editor<TaskProxy> {
		void markComplete();
		void markProgress();
		void deleteTask();
	}
}
