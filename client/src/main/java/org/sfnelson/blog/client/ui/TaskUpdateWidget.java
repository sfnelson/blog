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
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.request.TaskUpdateProxy;
import org.sfnelson.blog.client.views.TaskUpdateView;
import org.sfnelson.blog.client.widgets.*;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskUpdateWidget extends Composite implements TaskUpdateView, ContentWidget.HasContentEditor {

	interface Binder extends UiBinder<ArticlePanel, TaskUpdateWidget> {}

	interface Style extends CssResource {
		String controls();
		String changed();
		String editing();
		String article();
		String selected();
		String delete();
		String type();
		String content();
	}

	@UiField Style style;
	@UiField Anchor delete;
	@UiField Button save;
	@UiField Button cancel;

	@UiField TaskNameWidget task;
	@UiField TaskUpdateTypeWidget type;
	@UiField ContentWidget content;

	private boolean selected;
	private boolean editing;

	private EntryEditor<TaskUpdateProxy> editor;

	@Inject
	TaskUpdateWidget() {
		initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));

		content.setParent(this);

		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editor.requestSelect();
			}
		}, ClickEvent.getType());
	}

	@Override
	public ContentWidget getContentEditor() {
		return content;
	}

	@Override
	public TaskNameWidget getTaskNameEditor() {
		return task;
	}

	@Override
	public TaskUpdateTypeWidget getTypeEditor() {
		return type;
	}

	@Override
	public void setEditor(EntryEditor<TaskUpdateProxy> editor) {
		this.editor = editor;
	}

	@Override
	public void edit() {
		editing = true;
		addStyleName(style.editing());
	}

	@Override
	public void view() {
		editing = false;
		content.done();
		removeStyleName(style.editing());
	}

	@Override
	public void select() {
		selected = true;
		addStyleName(style.selected());
	}

	@Override
	public void deselect() {
		selected = false;
		removeStyleName(style.selected());
	}

	@Override
	public Editor<TaskUpdateProxy> asEditor() {
		return editor;
	}

	@Override
	public void startEditingContent() {
		if (selected) {
			content.edit();

			if (!editing) {
				editor.requestEdit();
			}
		}
	}

	@UiHandler("save")
	void save(ClickEvent ev) {
		editor.requestSubmit();
	}

	@UiHandler("cancel")
	void cancel(ClickEvent ev) {
		editor.requestCancel();
	}

	@UiHandler("delete")
	void delete(ClickEvent ev) {
		editor.requestDelete();
	}

	@Override
	public void focus() {
		startEditingContent();
	}
}