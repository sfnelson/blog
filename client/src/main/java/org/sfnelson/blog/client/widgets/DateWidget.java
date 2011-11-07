package org.sfnelson.blog.client.widgets;

import java.util.Date;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public class DateWidget extends ComplexPanel implements LeafValueEditor<Date> {

	private static final DateTimeFormat longFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601);
	private static final DateTimeFormat day = DateTimeFormat.getFormat("EEEE ");
	private static final DateTimeFormat date = DateTimeFormat.getFormat("d");
	private static final DateTimeFormat monthTime = DateTimeFormat.getFormat(" 'of' MMMM, h");
	private static final DateTimeFormat ampm = DateTimeFormat.getFormat("a");

	private Date value;

	public DateWidget() {
		setElement(DOM.createElement("TIME"));
	}

	@Override
	public void setValue(Date value) {
		if (value == null) {
			getElement().removeAttribute("datetime");
			getElement().setInnerText("");
		} else {
			getElement().setAttribute("datetime", longFormat.format(value));
			StringBuilder date = new StringBuilder();
			date.append(this.day.format(value));
			String d = this.date.format(value);
			date.append(d);
			switch (d.charAt(d.length() - 1)) {
				case '1':
					date.append("st");
					break;
				case '2':
					date.append("nd");
					break;
				case '3':
					date.append("rd");
					break;
				default:
					date.append("th");
					break;
			}
			date.append(monthTime.format(value));
			date.append(ampm.format(value).toLowerCase());
			getElement().setInnerText(date.toString());
		}

		this.value = value;
	}

	@Override
	public Date getValue() {
		return value;
	}
}
