package org.sfnelson.blog.client.views;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 6/11/11
 */
public interface ErrorView extends IsWidget {
	void showError(String message);
}
