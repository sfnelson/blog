package org.sfnelson.blog.client.views;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public interface BlogView extends IsWidget {
	void addEntry(int position, EntryView view);
	void removeEntry(EntryView view);
}
