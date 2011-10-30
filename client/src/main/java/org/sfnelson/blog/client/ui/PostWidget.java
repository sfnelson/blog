package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.views.PostView;
import org.sfnelson.blog.client.widgets.ArticlePanel;
import org.sfnelson.blog.client.widgets.ContentWidget;
import org.sfnelson.blog.client.widgets.DateWidget;
import org.sfnelson.blog.client.widgets.TitleWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class PostWidget extends Composite implements PostView, TitleWidget.HasTitleEditor,
		ContentWidget.HasContentEditor {

	interface Binder extends UiBinder<ArticlePanel, PostWidget> {}

	interface Style extends CssResource {
		String article();
		String controls();
		String editing();
		String changed();
		String selected();
		String delete();
	}

	private boolean selected;
	private boolean editing;

	@UiField Style style;

	@UiField TitleWidget title;
	@UiField DateWidget posted;
	@UiField ContentWidget content;

	@UiField Anchor delete;
	@UiField Button save;
	@UiField Button cancel;

	private EntryEditor<PostProxy> editor;

	@Inject
	PostWidget() {
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));

		title.setParent(this);
		content.setParent(this);

		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editor.requestSelect();
			}
		}, ClickEvent.getType());
	}

	@Override
	public void setEditor(EntryEditor<PostProxy> editor) {
		this.editor = editor;
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
	public Editor<PostProxy> asEditor() {
		return editor;
	}

	@Override
	public TitleWidget getTitleEditor() {
		return title;
	}

	@Override
	public DateWidget getPostedEditor() {
		return posted;
	}

	@Override
	public ContentWidget getContentEditor() {
		return content;
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
	public void edit() {
		addStyleName(style.editing());
		editing = true;
	}

	@Override
	public void view() {
		removeStyleName(style.editing());
		title.done();
		content.done();
		editing = false;
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
	public void startEditingContent() {
		if (selected) {
			content.edit();

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