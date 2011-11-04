package org.sfnelson.blog.client.editors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.Editor;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import org.sfnelson.blog.client.request.*;
import org.sfnelson.blog.client.views.UpdateView;
import org.sfnelson.blog.domain.Content;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class UpdateEditor extends EntryEditor<UpdateProxy, UpdateView> {

	interface Driver extends RequestFactoryEditorDriver<UpdateProxy, UpdateEditor> {}

	private final Driver driver = GWT.create(Driver.class);

	@Inject
	UpdateEditor(UpdateView view, Provider<EntryRequest> request, EventBus eventBus, RequestFactory rf) {
		super(view, request, eventBus, UpdateProxy.class);
		driver.initialize(rf, this);
	}

	ContentEditor content() {
		return getView().getContentEditor();
	}

	@Path("task.title")
	Editor<String> task() {
		return getView().getTaskNameEditor();
	}

	Editor<TaskUpdateType> type() {
		return getView().getTypeEditor();
	}

	@Override
	protected RequestFactoryEditorDriver<UpdateProxy, ? extends EntryEditor<UpdateProxy, ?>> getDriver() {
		return driver;
	}

	public UpdateProxy create(TaskProxy task, TaskUpdateType type) {
		EntryRequest context = edit();
		UpdateProxy value = context.create(UpdateProxy.class);
		value.setTask(task);
		value.setPosted(new Date());
		value.setType(type);
		ContentProxy content = context.create(ContentProxy.class);
		content.setType(Content.Type.WIKI);
		content.setValue("");
		value.setContent(content);
		context.createContent(content);
		context.createEntry(value);
		Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
			@Override
			public boolean execute() {
				requestSelect();
				getView().focus();
				return false;
			}
		}, 50);
		return create(value, context);
	}
}
