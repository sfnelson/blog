package org.sfnelson.blog.client.widgets;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class TitleWidget extends ComplexPanel implements LeafValueEditor<String> {

	private final Label label;

	private String value;

	public TitleWidget() {
		setElement(DOM.createElement("H1"));

		label = new Label();
		label.setStyleName("");

		add(label, getElement());
	}

	@Override
	public void setValue(String value) {
		label.setText(value);
	}

	@Override
	public String getValue() {
		return value;
	}
}