package org.sfnelson.blog.client.widgets;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 25/10/11
 */
public class RootPanel implements AcceptsOneWidget {

	private final String id;

	public RootPanel() {
		this.id = null;
	}

	public RootPanel(String id) {
		this.id = id;
	}

	@Override
	public void setWidget(IsWidget w) {
		com.google.gwt.user.client.ui.RootPanel panel;
		if (id != null) {
			panel = com.google.gwt.user.client.ui.RootPanel.get(id);
		} else {
			panel = com.google.gwt.user.client.ui.RootPanel.get();
		}
		if (w == null) {
			panel.clear();
		} else {
			panel.add(w);
		}
	}
}
