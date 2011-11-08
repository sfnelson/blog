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
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.request.UpdateProxy;
import org.sfnelson.blog.client.views.UpdateView;
import org.sfnelson.blog.client.widgets.ArticlePanel;
import org.sfnelson.blog.client.widgets.ContentWidget;
import org.sfnelson.blog.client.widgets.TaskNameWidget;
import org.sfnelson.blog.client.widgets.TaskUpdateTypeWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class UpdateViewer extends Composite implements RootEditor<UpdateProxy>, UpdateView,
		ValueAwareEditor<UpdateProxy> {
	interface Binder extends UiBinder<ArticlePanel, UpdateViewer> {
	}

	interface Driver extends RequestFactoryEditorDriver<UpdateProxy, UpdateViewer> {
	}

	interface Style extends CssResource {
		String selected();

		String type();

		String content();

		String article();
	}

	private final Driver driver;
	private final EventBus eventBus;

	@UiField
	@Ignore
	Style style;

	@UiField
	@Ignore
	ArticlePanel root;

	@UiField
	TaskUpdateTypeWidget type;

	@UiField
	TaskNameWidget task;

	@UiField
	ContentWidget content;

	private UpdateProxy value;
	private HandlerRegistration registration;
	private EditorDelegate<UpdateProxy> delegate;

	@Inject
	UpdateViewer(RequestFactory rf, EventBus eventBus) {
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));

		this.driver = GWT.create(Driver.class);
		this.eventBus = eventBus;
		driver.initialize(rf, this);
	}

	@Override
	@Ignore
	public RootEditor<UpdateProxy> asEditor() {
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
	public <X extends UpdateProxy> X create(Class<X> type, Initializer<X> initializer) {
		throw new UnsupportedOperationException("this view cannot edit");
	}

	@Override
	public void init(UpdateProxy value) {
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

	@Override
	public void focus() {
		root.setFocus(true);
	}

	@UiHandler("root")
	void onClick(ClickEvent ev) {
		if (value != null) {
			eventBus.fireEvent(new EditorSelectionEvent(value, EditorSelectionEvent.Type.SELECT));
		}
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
	public void setValue(UpdateProxy value) {
		this.value = value;

		if (registration == null && delegate != null) {
			registration = delegate.subscribe();
		}
	}

	@Override
	public void setDelegate(EditorDelegate<UpdateProxy> delegate) {
		this.delegate = delegate;
	}
}