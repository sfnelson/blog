package org.sfnelson.blog.client.widgets;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Composite;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 27/10/11
 */
public class ContentWidget extends Composite implements LeafValueEditor<String> {

	public interface HasContentEditor {
		void startEditingContent();
	}

	private final FlowPanel container;
	private final HTML content;
	private final TextArea edit;

	private HasContentEditor parent;

	public ContentWidget() {
		container = new FlowPanel();
		content = new HTML();
		edit = new TextArea();

		initWidget(container);
		container.add(content);

		content.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				parent.startEditingContent();
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

	public void setParent(HasContentEditor parent) {
		this.parent = parent;
	}

	@Override
	public void setValue(String value) {
		content.setHTML(value);
		edit.setValue(value);
	}

	@Override
	public String getValue() {
		return edit.getValue();
	}

	public void edit() {
		container.clear();
		container.add(edit);

		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				edit.setFocus(true);
			}
		});
	}

	public void done() {
		container.clear();
		content.setHTML(edit.getValue());
		container.add(content);
	}
}
