package org.sfnelson.blog.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Cookies;
import org.sfnelson.blog.client.places.ManagerPlace;
import org.sfnelson.blog.client.widgets.RootPanel;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 25/10/11
 */
public class AdminEntryPoint implements EntryPoint {

	private final Injector injector = GWT.create(Injector.class);

	@Override
	public void onModuleLoad() {
		EventBus eventBus = injector.getEventBus();

		injector.getAdminApp().start(new RootPanel("navigation"), eventBus);

		new ActivityManager(injector.getAuth(), eventBus).setDisplay(new RootPanel("auth"));
		new ActivityManager(injector.getBlog(), eventBus).setDisplay(new RootPanel("entries"));
		new ActivityManager(injector.getTasks(), eventBus).setDisplay(new RootPanel("tasks"));

		PlaceController pc = injector.getPlaceController();
		HistoryMapper hm = injector.getHistoryMapper();

		PlaceHistoryHandler hh = new PlaceHistoryHandler(hm);
		hh.register(pc, (com.google.web.bindery.event.shared.EventBus) eventBus, ManagerPlace.PLACE);

		hh.handleCurrentHistory();
	}
}
