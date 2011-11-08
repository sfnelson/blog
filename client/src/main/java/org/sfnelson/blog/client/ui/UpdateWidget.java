package org.sfnelson.blog.client.ui;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.EntityProxyChange;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.google.web.bindery.requestfactory.shared.WriteOperation;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.editors.RootEditor;
import org.sfnelson.blog.client.request.ContentProxy;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.request.UpdateProxy;
import org.sfnelson.blog.client.views.UpdateView;
import org.sfnelson.blog.client.widgets.ArticlePanel;
import org.sfnelson.blog.client.widgets.TaskNameWidget;
import org.sfnelson.blog.client.widgets.TaskUpdateTypeWidget;
import org.sfnelson.blog.domain.Content;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class UpdateWidget extends Composite implements UpdateView, RootEditor.HasDelegates {

	interface Driver extends RequestFactoryEditorDriver<UpdateProxy, UpdateEditor> {
	}

	interface Binder extends UiBinder<ArticlePanel, UpdateWidget> {
	}

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

	class UpdateEditor extends org.sfnelson.blog.client.editors.EntryEditor<UpdateProxy, UpdateWidget> {
		private final EventBus eventBus;

		public UpdateEditor(Provider<EntryRequest> rq, EventBus eb) {
			super(UpdateWidget.this, rq, eb, UpdateProxy.class);
			this.eventBus = eb;
		}

		ContentEditorWidget content() {
			return content;
		}

		TaskNameWidget task() {
			return task;
		}

		TaskUpdateTypeWidget type() {
			return type;
		}

		@Override
		protected <X extends UpdateProxy> X doCreate(Class<X> type, Initializer<X> initializer) {
			EntryRequest context = getRequest();
			ContentProxy content = context.create(ContentProxy.class);
			content.setType(Content.Type.WIKI);
			final X update = context.create(type);
			update.setPosted(new Date());
			update.setContent(content);
			if (initializer != null) {
				initializer.initialize(update);
			}
			context.createContent(content);
			context.createEntry(update).to(new Receiver<Void>() {
				@Override
				public void onFailure(ServerFailure error) {
					eventBus.fireEventFromSource(
							new EntityProxyChange<UpdateProxy>(update, WriteOperation.DELETE),
							UpdateProxy.class);
				}

				@Override
				public void onSuccess(Void response) {
				}
			});
			getDriver().edit(update, context);
			return update;
		}

		@Override
		protected void doEdit(final UpdateProxy update) {
			EntryRequest context = getRequest();
			getDriver().edit(update, context);
			if (update != null) {
				context.updateContent(update.getContent()).to(new Receiver<Void>() {
					@Override
					public void onFailure(ServerFailure error) {
						eventBus.fireEventFromSource(
								new EntityProxyChange<ContentProxy>(update.getContent(), WriteOperation.UPDATE),
								ContentProxy.class);
						super.onFailure(error);
					}

					@Override
					public void onSuccess(Void response) {
					}
				});
				context.updateEntry(update).to(new Receiver<Void>() {
					@Override
					public void onFailure(ServerFailure error) {
						eventBus.fireEventFromSource(
								new EntityProxyChange<UpdateProxy>(update, WriteOperation.UPDATE),
								UpdateProxy.class);
						super.onFailure(error);
					}

					@Override
					public void onSuccess(Void response) {
					}
				});
			}
		}

		@Override
		protected RequestFactoryEditorDriver<UpdateProxy, ?> getDriver() {
			return driver;
		}
	}

	private final Driver driver;
	private final UpdateEditor editor;

	@UiField
	Style style;
	@UiField
	Anchor delete;
	@UiField
	Button save;
	@UiField
	Button cancel;

	@UiField
	ArticlePanel root;
	@UiField
	TaskNameWidget task;
	@UiField
	TaskUpdateTypeWidget type;
	@UiField
	ContentEditorWidget content;

	private boolean selected;
	private boolean editing;

	@Inject
	UpdateWidget(RequestFactory rf, Provider<EntryRequest> rq, EventBus eb) {
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));

		driver = GWT.create(Driver.class);
		editor = new UpdateEditor(rq, eb);
		driver.initialize(rf, editor);

		content.setParent(this);

		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editor.requestSelect();
			}
		}, ClickEvent.getType());
	}

	@Override
	public UpdateEditor asEditor() {
		return editor;
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
	public boolean select() {
		selected = true;
		root.setFocus(true);
		addStyleName(style.selected());
		return true;
	}

	@Override
	public boolean deselect() {
		selected = false;
		removeStyleName(style.selected());
		return true;
	}

	@Override
	public void startEditing(IsWidget widget) {
		if (selected) {
			if (widget == content) {
				content.edit();
			}

			if (!editing) {
				editor.requestEdit();
			}
		}
	}

	@Override
	public void editPrevious(IsWidget current) {
		if (current == content) {
			content.done();
			cancel.setFocus(true);
		}
	}

	@Override
	public void editNext(IsWidget current) {
		if (current == content) {
			content.done();
			save.setFocus(true);
		}
	}

	@Override
	public void doneEditing(IsWidget current) {
		if (current == content) {
			content.done();
		}
		root.setFocus(true);
	}

	@Override
	public void cancelEditing(IsWidget current) {
		if (current == content) {
			content.done();
		}
		root.setFocus(true);
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
		startEditing(content);
	}
}