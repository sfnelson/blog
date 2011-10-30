package org.sfnelson.blog.client.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 28/10/11
 */
public class FooterPanel extends ComplexPanel {

	public FooterPanel() {
		setElement(DOM.createElement("FOOTER"));
	}

	public void add(Widget widget) {
		add(widget, getElement());
	}
}
