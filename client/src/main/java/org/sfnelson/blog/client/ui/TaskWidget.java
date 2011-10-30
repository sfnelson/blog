package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.sfnelson.blog.client.views.TaskView;
import org.sfnelson.blog.client.views.TasksView;
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.widgets.TitleWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class TaskWidget extends Composite implements TaskView, TitleWidget.HasTitleEditor {

	interface Binder extends UiBinder<HTMLPanel, TaskWidget> {}

	interface Style extends CssResource {
		String widget();
		String marks();
		String complete();
		String progress();
		String abandon();
		String editing();
		String selected();
		String controls();
	}

	@UiField Style style;

	@UiField TitleWidget title;

	@UiField Anchor complete;
	@UiField Anchor partial;
	@UiField Anchor abandon;
	@UiField Button save;
	@UiField Button cancel;

	private Editor editor;

	private boolean editing;
	private boolean selected;

	@Inject
	TaskWidget() {
		initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));

		title.setParent(this);

		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editor.requestSelect();
			}
		}, ClickEvent.getType());
	}

	@Override
	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public TitleWidget getTitleEditor() {
		return title;
	}

	@UiHandler("complete")
	void complete(ClickEvent event) {
		editor.markComplete();
	}

	@UiHandler("partial")
	void progress(ClickEvent event) {
		editor.markProgress();
	}

	@UiHandler("abandon")
	void abandon(ClickEvent event) {
		editor.markAbandoned();
	}

	@UiHandler("save")
	void save(ClickEvent event) {
		editor.requestSubmit();
	}

	@UiHandler("cancel")
	void cancel(ClickEvent event) {
		editor.requestCancel();
	}

	@Override
	public void edit() {
		addStyleName(style.editing());
		editing = true;
	}

	@Override
	public void view() {
		removeStyleName(style.editing());
		title.done();
		editing = false;
	}

	@Override
	public void select() {
		addStyleName(style.selected());
		selected = true;
	}

	@Override
	public void deselect() {
		removeStyleName(style.selected());
		selected = false;
	}

	@Override
	public Editor asEditor() {
		return editor;
	}

	@Override
	public void startEditingTitle() {
		if (selected) {
			title.edit();

			if (!editing) {
				editor.requestEdit();
			}
		}
	}

	@Override
	public void focus() {
		startEditingTitle();
	}
}