package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.sfnelson.blog.client.views.TaskView;
import org.sfnelson.blog.client.views.TasksView;
import org.sfnelson.blog.client.request.TaskProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class TaskWidget extends Composite implements TaskView {
	interface TaskWidgetUiBinder extends UiBinder<HTMLPanel, TaskWidget> {
	}

	private static TaskWidgetUiBinder ourUiBinder = GWT.create(TaskWidgetUiBinder.class);

	@UiField Label title;
	@UiField Anchor complete;
	@UiField Anchor partial;
	@UiField Anchor delete;

	private TaskView.Presenter presenter;

	@Inject
	TaskWidget() {
		initWidget(ourUiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	public Editor<String> getTitleEditor() {
		return title.asEditor();
	}

	@UiHandler("complete")
	void complete(ClickEvent event) {
		presenter.markComplete();
	}

	@UiHandler("partial")
	void progress(ClickEvent event) {
		presenter.markProgress();
	}

	@UiHandler("delete")
	void delete(ClickEvent event) {
		presenter.deleteTask();
	}
}