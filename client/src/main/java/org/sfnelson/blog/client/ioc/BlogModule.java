package org.sfnelson.blog.client.ioc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.sfnelson.blog.client.*;
import org.sfnelson.blog.client.activities.ShowBlog;
import org.sfnelson.blog.client.activities.ShowEditableBlog;
import org.sfnelson.blog.client.request.AuthRequest;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.request.TaskRequest;
import org.sfnelson.blog.client.ui.*;
import org.sfnelson.blog.client.views.*;
import org.sfnelson.blog.client.widgets.EditableEntryList;
import org.sfnelson.blog.client.widgets.EntryList;
import org.sfnelson.blog.client.widgets.ErrorPanel;
import org.sfnelson.blog.client.widgets.NavWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class BlogModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

		bind(BlogView.class).annotatedWith(ViewOnly.class).to(EntryList.class).in(Singleton.class);
		bind(BlogView.class).annotatedWith(Author.class).to(EditableEntryList.class).in(Singleton.class);

		bind(ShowBlog.class).annotatedWith(ViewOnly.class).to(ShowBlog.class);
		bind(ShowBlog.class).annotatedWith(Author.class).to(ShowEditableBlog.class);

		bind(PostView.class).annotatedWith(ViewOnly.class).to(PostViewer.class);
		bind(PostView.class).annotatedWith(Author.class).to(PostWidget.class);

		bind(UpdateView.class).annotatedWith(ViewOnly.class).to(UpdateViewer.class);
		bind(UpdateView.class).annotatedWith(Author.class).to(UpdateWidget.class);

		bind(TasksView.class).to(TaskList.class).in(Singleton.class);
		bind(AuthView.class).to(AuthWidget.class).in(Singleton.class);
		bind(ErrorView.class).to(ErrorPanel.class).in(Singleton.class);


		bind(TaskView.class).to(TaskWidget.class);


		bind(AuthViewMapper.class).in(Singleton.class);
		bind(NavViewMapper.class).in(Singleton.class);
		bind(EntryViewMapper.class).in(Singleton.class);
		bind(TaskViewMapper.class).in(Singleton.class);
	}

	@Provides
	public com.google.web.bindery.event.shared.EventBus getEventBus(EventBus eventBus) {
		return eventBus;
	}

	@Provides
	@Singleton
	public RequestFactory getRequestFactory(EventBus eventBus) {
		RequestFactory rf = GWT.create(RequestFactory.class);
		rf.initialize(eventBus);
		return rf;
	}

	@Provides
	@Singleton
	public PlaceController getPlaceController(EventBus eventBus) {
		return new PlaceController((com.google.web.bindery.event.shared.EventBus) eventBus);
	}

	@Provides
	@Singleton
	public HistoryMapper getHistoryMapper() {
		return GWT.create(HistoryMapper.class);
	}

	@Provides
	public TaskRequest taskRequest(RequestFactory rf) {
		return rf.taskRequest();
	}

	@Provides
	public EntryRequest entryRequest(RequestFactory rf) {
		return rf.entryRequest();
	}

	@Provides
	public AuthRequest authRequest(RequestFactory rf) {
		return rf.authRequest();
	}

	@Provides
	@Singleton
	public NavigationView<Scheduler.ScheduledCommand> getNavigationView() {
		return new NavWidget<Scheduler.ScheduledCommand>();
	}
}
