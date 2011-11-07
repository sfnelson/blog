package org.sfnelson.blog.client.ui;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.EntityProxyChange;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.google.web.bindery.requestfactory.shared.WriteOperation;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.editors.ModalEditor;
import org.sfnelson.blog.client.editors.RootEditor;
import org.sfnelson.blog.client.events.CreateUpdateEvent;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.request.TaskRequest;
import org.sfnelson.blog.client.views.TaskView;
import org.sfnelson.blog.client.widgets.TitleWidget;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class TaskWidget extends Composite implements TaskView, RootEditor.HasDelegates {

	interface Driver extends RequestFactoryEditorDriver<TaskProxy, TaskEditor> {
	}

	interface Binder extends UiBinder<HTMLPanel, TaskWidget> {
	}

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

	class TaskEditor extends ModalEditor<TaskProxy, TaskWidget> implements TaskView.TaskEditor {
		public TaskEditor(EventBus eb) {
			super(TaskWidget.this, eb, TaskProxy.class);
		}

		TitleWidget title() {
			return title;
		}

		@Override
		protected RequestFactoryEditorDriver<TaskProxy, ?> getDriver() {
			return driver;
		}

		@Override
		protected <X extends TaskProxy> X doCreate(Class<X> type, Initializer<X> initializer) {
			TaskRequest rq = request.get();
			final X value = rq.create(type);
			value.setCreated(new Date());
			if (initializer != null) {
				initializer.initialize(value);
			}
			rq.createTask(value).to(new Receiver<Void>() {
				@Override
				public void onFailure(ServerFailure error) {
					eventBus.fireEventFromSource(
							new EntityProxyChange(value, WriteOperation.DELETE),
							TaskProxy.class);
					super.onFailure(error);
				}

				@Override
				public void onSuccess(Void response) {
				}
			});
			driver.edit(value, rq);
			return value;
		}

		@Override
		protected void doEdit(final TaskProxy value) {
			TaskRequest rq = request.get();
			rq.updateTask(value).to(new Receiver<Void>() {
				@Override
				public void onFailure(ServerFailure error) {
					eventBus.fireEventFromSource(
							new EntityProxyChange(value, WriteOperation.UPDATE),
							TaskProxy.class);
					super.onFailure(error);
				}

				@Override
				public void onSuccess(Void response) {
				}
			});
			driver.edit(value, rq);
		}
	}

	private final Provider<TaskRequest> request;
	private final Driver driver;
	private final TaskEditor editor;
	private final EventBus eventBus;

	@UiField
	Style style;

	@UiField
	TitleWidget title;

	@UiField
	Anchor complete;
	@UiField
	Anchor partial;
	@UiField
	Anchor abandon;
	@UiField
	Button save;
	@UiField
	Button cancel;

	private boolean editing;
	private boolean selected;

	@Inject
	TaskWidget(RequestFactory rf, Provider<TaskRequest> rq, EventBus eb) {
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));

		this.request = rq;
		this.eventBus = eb;
		this.driver = GWT.create(Driver.class);
		this.editor = new TaskEditor(eb);
		driver.initialize(rf, editor);

		title.setParent(this);

		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editor.requestSelect();
			}
		}, ClickEvent.getType());
	}

	@Override
	public RootEditor<TaskProxy> asEditor() {
		return editor;
	}

	@UiHandler("complete")
	void complete(ClickEvent event) {
		eventBus.fireEvent(new CreateUpdateEvent(editor.getValue(), TaskUpdateType.COMPLETED));
	}

	@UiHandler("partial")
	void progress(ClickEvent event) {
		eventBus.fireEvent(new CreateUpdateEvent(editor.getValue(), TaskUpdateType.PROGRESS));
	}

	@UiHandler("abandon")
	void abandon(ClickEvent event) {
		eventBus.fireEvent(new CreateUpdateEvent(editor.getValue(), TaskUpdateType.ABANDONED));
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
	public void startEditing(IsWidget delegate) {
		if (selected) {
			if (delegate == title) title.edit();

			if (!editing) {
				editor.requestEdit();
			}
		}
	}

	@Override
	public void editPrevious(IsWidget currentDelegate) {
		title.done();
		cancel.setFocus(true);
	}

	@Override
	public void editNext(IsWidget currentDelegate) {
		title.done();
		save.setFocus(true);
	}

	@Override
	public void doneEditing(IsWidget delegate) {
		title.done();
	}

	@Override
	public void cancelEditing(IsWidget delegate) {
		title.cancel();
	}

	@Override
	public void focus() {
		startEditing(title);
	}
}
