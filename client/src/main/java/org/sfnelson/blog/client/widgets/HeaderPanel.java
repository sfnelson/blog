package org.sfnelson.blog.client.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 27/10/11
 */
public class HeaderPanel extends ComplexPanel implements HasWidgets {

	public HeaderPanel() {
		setElement(DOM.createElement("HEADER"));
	}

	@Override
	public void add(Widget child) {
		add(child, getElement());
	}
}
