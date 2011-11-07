package org.sfnelson.blog.client;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import org.sfnelson.blog.client.events.CreatePostEvent;
import org.sfnelson.blog.client.events.CreateTaskEvent;
import org.sfnelson.blog.client.views.NavigationView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public class AdminApp extends AbstractActivity implements NavigationView.Presenter<Scheduler.ScheduledCommand> {

	private final NavigationView view;

	@Inject
	AdminApp(NavigationView<Scheduler.ScheduledCommand> view) {
		this.view = view;
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		view.setPresenter(this);

		view.addTarget(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				eventBus.fireEvent(new CreatePostEvent());
			}
		}, "Create Post");

		view.addTarget(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				eventBus.fireEvent(new CreateTaskEvent());
			}
		}, "Create Task");

		panel.setWidget(view);
	}

	@Override
	public void onStop() {
		view.clear();
	}

	@Override
	public void fire(Scheduler.ScheduledCommand command) {
		Scheduler.get().scheduleDeferred(command);
	}
}
