package org.sfnelson.blog.client.views;

import com.google.gwt.user.client.ui.IsWidget;

import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.util.EditorList;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public interface TasksView extends IsWidget {

	EditorList<TaskProxy> getList();

}
