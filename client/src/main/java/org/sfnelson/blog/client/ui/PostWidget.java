package org.sfnelson.blog.client.ui;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.views.PostView;
import org.sfnelson.blog.client.widgets.ArticlePanel;
import org.sfnelson.blog.client.widgets.DateWidget;
import org.sfnelson.blog.client.widgets.TitleEditorWidget;
import org.sfnelson.blog.domain.Content;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class PostWidget extends Composite implements PostView, RootEditor.HasDelegates {

	interface Driver extends RequestFactoryEditorDriver<PostProxy, PostEditor> {
	}

	interface Binder extends UiBinder<ArticlePanel, PostWidget> {
	}

	interface Style extends CssResource {
		String article();

		String controls();

		String editing();

		String changed();

		String selected();

		String delete();
	}

	class PostEditor extends org.sfnelson.blog.client.editors.EntryEditor<PostProxy, PostWidget> {
		private final EventBus eventBus;

		public PostEditor(Provider<EntryRequest> request, EventBus eventBus) {
			super(PostWidget.this, request, eventBus, PostProxy.class);
			this.eventBus = eventBus;
		}

		@Override
		protected RequestFactoryEditorDriver<PostProxy, ?> getDriver() {
			return driver;
		}

		TitleEditorWidget title() {
			return title;
		}

		DateWidget posted() {
			return posted;
		}

		ContentEditorWidget content() {
			return content;
		}

		@Override
		protected <X extends PostProxy> X doCreate(Class<X> type, Initializer<X> initializer) {
			EntryRequest context = getRequest();
			ContentProxy content = context.create(ContentProxy.class);
			content.setType(Content.Type.WIKI);
			final X post = context.create(type);
			post.setPosted(new Date());
			post.setTitle(DateTimeFormat.getFormat("EEEE").format(new Date()));
			post.setContent(content);
			if (initializer != null) {
				initializer.initialize(post);
			}
			context.createContent(content);
			context.createEntry(post).to(new Receiver<Void>() {
				@Override
				public void onFailure(ServerFailure error) {
					eventBus.fireEventFromSource(
							new EntityProxyChange<PostProxy>(post, WriteOperation.DELETE),
							PostProxy.class);
					super.onFailure(error);
				}

				@Override
				public void onSuccess(Void response) {
				}
			});
			getDriver().edit(post, context);
			return post;
		}

		@Override
		protected void doEdit(final PostProxy post) {
			EntryRequest context = getRequest();
			getDriver().edit(post, context);
			if (post != null) {
				context.updateContent(post.getContent()).to(new Receiver<Void>() {
					@Override
					public void onFailure(ServerFailure error) {
						eventBus.fireEventFromSource(
								new EntityProxyChange<ContentProxy>(post.getContent(), WriteOperation.UPDATE),
								ContentProxy.class);
						super.onFailure(error);
					}

					@Override
					public void onSuccess(Void response) {
					}
				});
				context.updateEntry(post).to(new Receiver<Void>() {
					@Override
					public void onFailure(ServerFailure error) {
						eventBus.fireEventFromSource(
								new EntityProxyChange<PostProxy>(post, WriteOperation.UPDATE),
								PostProxy.class);
						super.onFailure(error);
					}

					@Override
					public void onSuccess(Void response) {
					}
				});
			}
		}
	}

	private final Driver driver;
	private final PostEditor editor;

	private boolean selected;
	private boolean editing;

	@UiField
	Style style;

	@UiField
	ArticlePanel root;
	@UiField
	TitleEditorWidget title;
	@UiField
	DateWidget posted;
	@UiField
	ContentEditorWidget content;

	@UiField
	Anchor delete;
	@UiField
	Button save;
	@UiField
	Button cancel;

	@Inject
	PostWidget(RequestFactory rf, Provider<EntryRequest> rq, EventBus eb) {
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));

		driver = GWT.create(Driver.class);
		editor = new PostEditor(rq, eb);
		driver.initialize(rf, editor);

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
	public PostEditor asEditor() {
		return editor;
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
	public void startEditing(IsWidget widget) {
		if (selected) {
			if (widget == title) {
				title.edit();
			} else if (widget == content) {
				content.edit();
			}

			if (!editing) {
				editor.requestEdit();
			}
		}
	}

	@Override
	public void editPrevious(IsWidget current) {
		if (current == title) {
			title.done();
			cancel.setFocus(true);
		}
		if (current == content) {
			content.done();
			title.edit();
		}
	}

	@Override
	public void editNext(IsWidget current) {
		if (current == title) {
			title.done();
			content.edit();
		} else if (current == content) {
			content.done();
			save.setFocus(true);
		}
	}

	@Override
	public void doneEditing(IsWidget current) {
		if (current == title) {
			title.cancel();
		} else if (current == content) {
			content.done();
		}
		root.setFocus(true);
	}

	@Override
	public void cancelEditing(IsWidget current) {
		if (current == title) {
			title.cancel();
		} else if (current == content) {
			content.done();
		}
		root.setFocus(true);
	}

	@Override
	public void focus() {
		startEditing(title);
	}
}