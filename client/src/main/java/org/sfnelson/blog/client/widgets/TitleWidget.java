package org.sfnelson.blog.client.widgets;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 27/10/11
 */
public class TitleWidget<T> extends ComplexPanel implements LeafValueEditor<String> {

	public interface HasTitleEditor {
		void startEditingTitle();
	}

	private final Label label;
	private final TextBox edit;

	private HasTitleEditor parent;

	public TitleWidget() {
		setElement(DOM.createElement("H1"));

		label = new Label();
		label.setStyleName("");
		edit = new TextBox();

		add(label);

		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				parent.startEditingTitle();
			}
		});

		edit.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
					@Override
					public boolean execute() {
						done();
						return false;
					}
				}, 100);
			}
		});
	}

	public void add(Widget w) {
		add(w, getElement());
	}

	public void setParent(HasTitleEditor parent) {
		this.parent = parent;
	}

	@Override
	public void setValue(String value) {
		label.setText(value);
		edit.setValue(value);
	}

	@Override
	public String getValue() {
		return edit.getValue();
	}

	public void edit() {
		clear();
		add(edit);

		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				edit.setFocus(true);
			}
		});
	}

	public void done() {
		clear();
		label.setText(getValue());
		add(label);
	}
}
