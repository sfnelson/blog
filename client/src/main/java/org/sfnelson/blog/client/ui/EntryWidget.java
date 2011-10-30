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
import org.sfnelson.blog.client.views.EntryView;
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.widgets.ArticlePanel;
import org.sfnelson.blog.client.widgets.ContentWidget;
import org.sfnelson.blog.client.widgets.DateWidget;
import org.sfnelson.blog.client.widgets.TitleWidget;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class EntryWidget extends Composite implements EntryView, TitleWidget.HasTitleEditor,
		ContentWidget.HasContentEditor {

	interface Binder extends UiBinder<ArticlePanel, EntryWidget> {}

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

	private Presenter presenter;

	@Inject
	EntryWidget() {
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));

		title.setParent(this);
		content.setParent(this);

		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.requestSelect();
			}
		}, ClickEvent.getType());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
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
		return null;
	}

	@Override
	public Editor<String> getTitleEditor() {
		return title;
	}

	@Override
	public Editor<Date> getPostedEditor() {
		return posted;
	}

	@Override
	public Editor<String> getContentEditor() {
		return content;
	}

	@UiHandler("save")
	void save(ClickEvent ev) {
		presenter.requestSubmit();
	}

	@UiHandler("cancel")
	void cancel(ClickEvent ev) {
		presenter.requestCancel();
	}

	@UiHandler("delete")
	void delete(ClickEvent ev) {
		presenter.requestDelete();
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
				presenter.requestEdit();
			}
		}
	}

	@Override
	public void startEditingContent() {
		if (selected) {
			content.edit();

			if (!editing) {
				presenter.requestEdit();
			}
		}
	}
}