package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.editors.EditorSource;
import org.sfnelson.blog.client.editors.ModalEditor;
import org.sfnelson.blog.client.request.TaskProxy;
import org.sfnelson.blog.client.util.EditorList;
import org.sfnelson.blog.client.views.TaskView;
import org.sfnelson.blog.client.views.TasksView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class TaskList extends Composite implements TasksView {
	interface Binder extends UiBinder<FlowPanel, TaskList> {
	}

	private class Source implements EditorSource<TaskProxy> {
		@Override
		@SuppressWarnings("unchecked")
		public <X extends TaskProxy> ModalEditor<X, ?> create(int index, Class<X> type) {
			TaskView view = viewProvider.get();
			tasks.insert(view, index);
			return (ModalEditor<X, ?>) view.asEditor();
		}

		@Override
		public void dispose(Object o) {
			if (o instanceof TaskView.TaskEditor) {
				tasks.remove(((TaskView.TaskEditor) o).asWidget());
			}
		}
	}

	private final Provider<TaskView> viewProvider;
	private final EditorList<TaskProxy> list;

	@UiField
	FlowPanel tasks;

	@Inject
	TaskList(Provider<TaskView> viewProvider) {
		this.viewProvider = viewProvider;
		this.list = new EditorList<TaskProxy>(new Source());

		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));
	}

	@Override
	public EditorList<TaskProxy> getList() {
		return list;
	}
}