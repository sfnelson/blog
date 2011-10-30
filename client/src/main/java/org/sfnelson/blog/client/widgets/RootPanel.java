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
		if (id != null) {
			com.google.gwt.user.client.ui.RootPanel.get(id).add(w);
		}
		else {
			com.google.gwt.user.client.ui.RootPanel.get().add(w);
		}
	}
}
