package org.sfnelson.blog.client.editors;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.request.TaskRequest;
import org.sfnelson.blog.client.views.TaskView;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 30/10/11
 */
public class TaskEditor extends AbstractActivity implements ValueAwareEditor<TaskProxy>, TaskView.Presenter {

	interface Driver extends RequestFactoryEditorDriver<TaskProxy, TaskEditor> {};

	private final TaskView view;

	Editor<String> title() {
		return view.getTitleEditor();
	}

	private final Provider<TaskRequest> request;
	private final Driver driver = GWT.create(Driver.class);

	private TaskProxy reset;
	private TaskProxy value;
	private TaskRequest edit;
	private boolean created;

	@Inject
	TaskEditor(TaskView view, Provider<TaskRequest> request, RequestFactory rf) {
		this.view = view;
		this.request = request;

		driver.initialize(rf, this);

		view.setPresenter(this);
	}

	@Ignore
	public TaskView getView() {
		return view;
	}

	public TaskProxy create() {
		created = true;
		edit = request.get();
		value = edit.create(TaskProxy.class);
		value.setTitle("New Task");
		driver.edit(value, edit);
		return value;
	}

	public void init(TaskProxy value) {
		created = false;
		edit = null;
		this.value = value;
		driver.display(value);
	}

	@Override
	public void markComplete() {
		TaskRequest rq = request.get();
		TaskProxy task = rq.edit(value);
		task.setCompleted(new Date());
		rq.updateTask(task);
		rq.fire();
	}

	@Override
	public void markProgress() {
		TaskRequest rq = request.get();
		TaskProxy task = rq.edit(value);
		task.setUpdated(new Date());
		rq.updateTask(task);
		rq.fire();
	}

	@Override
	public void deleteTask() {
		TaskRequest rq = request.get();
		TaskProxy task = rq.edit(value);
		task.setAbandoned(new Date());
		rq.updateTask(task);
		rq.fire();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
	}

	@Override
	public void flush() {
	}

	@Override
	public void onPropertyChange(String... paths) {
	}

	@Override
	public void setValue(TaskProxy value) {
		this.reset = this.value;
		this.value = value;
	}

	@Override
	public void setDelegate(EditorDelegate<TaskProxy> taskProxyEditorDelegate) {
	}
}
