package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;

import com.google.inject.Inject;
import org.sfnelson.blog.client.editors.RootEditor;
import org.sfnelson.blog.client.events.EditorSelectionEvent;
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.views.PostView;
import org.sfnelson.blog.client.widgets.ArticlePanel;
import org.sfnelson.blog.client.widgets.ContentWidget;
import org.sfnelson.blog.client.widgets.DateWidget;
import org.sfnelson.blog.client.widgets.TitleWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class PostViewer extends Composite implements RootEditor<PostProxy>, PostView, ValueAwareEditor<PostProxy> {
	interface Binder extends UiBinder<ArticlePanel, PostViewer> {
	}

	interface Driver extends RequestFactoryEditorDriver<PostProxy, PostViewer> {
	}

	interface Style extends CssResource {
		String article();

		String selected();
	}

	private final Driver driver;
	private final EventBus eventBus;

	@UiField
	@Ignore
	ArticlePanel root;

	@UiField
	@Ignore
	Style style;

	@UiField
	TitleWidget title;

	@UiField
	DateWidget posted;

	@UiField
	ContentWidget content;

	private PostProxy value;
	private HandlerRegistration registration;
	private EditorDelegate<PostProxy> delegate;

	@Inject
	PostViewer(RequestFactory rf, EventBus eventBus) {
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));
		this.eventBus = eventBus;
		driver = GWT.create(Driver.class);
		driver.initialize(rf, this);
	}

	@Override
	public void focus() {
		root.setFocus(true);
	}

	@Override
	@Ignore
	public RootEditor<PostProxy> asEditor() {
		return this;
	}

	@Override
	public void edit() {
		// no-op
	}

	@Override
	public void view() {
		// no-op
	}

	@Override
	public <X extends PostProxy> X create(Class<X> type, Initializer<X> initializer) {
		throw new UnsupportedOperationException("This post is view-only");
	}

	@Override
	public void init(PostProxy value) {
		driver.display(value);
	}

	@Override
	public boolean select() {
		addStyleName(style.selected());
		root.setFocus(true);
		return true;
	}

	@Override
	public boolean deselect() {
		removeStyleName(style.selected());
		return true;
	}

	@UiHandler("root")
	void onClick(ClickEvent ev) {
		eventBus.fireEvent(new EditorSelectionEvent(value, EditorSelectionEvent.Type.SELECT));
	}

	@Override
	public void flush() {
		// no-op
	}

	@Override
	public void onPropertyChange(String... paths) {
		// no-op
	}

	@Override
	public void setValue(PostProxy value) {
		this.value = value;

		if (registration == null && delegate != null) {
			this.registration = delegate.subscribe();
		}
	}

	@Override
	public void setDelegate(EditorDelegate<PostProxy> delegate) {
		this.delegate = delegate;
	}
}