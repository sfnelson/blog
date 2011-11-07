package org.sfnelson.blog.client.views;

import com.google.gwt.user.client.ui.IsWidget;

import org.sfnelson.blog.client.request.EntryProxy;
import org.sfnelson.blog.client.util.EditorList;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public interface BlogView extends IsWidget {
	EditorList<EntryProxy> getList();
}
