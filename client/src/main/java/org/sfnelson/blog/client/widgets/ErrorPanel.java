package org.sfnelson.blog.client.widgets;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.sfnelson.blog.client.BlogResources;
import org.sfnelson.blog.client.views.ErrorView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 6/11/11
 */
public class ErrorPanel extends Composite implements ErrorView {

	private final SimplePanel container;
	private final Label message;
	private final BlogResources.Style style;

	@Inject
	ErrorPanel(BlogResources admin) {
		this.style = admin.style();

		message = new Label();
		message.setStylePrimaryName(style.errorMessage());
		message.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		container = new SimplePanel();
		container.setStylePrimaryName(style.error());
		container.add(message);

		initWidget(container);
	}

	@Override
	public void showError(String message) {
		this.message.setText(message);
		container.addStyleName(style.show());
		Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
			@Override
			public boolean execute() {
				hide();
				return false;
			}
		}, 5000);
	}
	
	void hide() {
		container.removeStyleName(style.show());
	}
}
