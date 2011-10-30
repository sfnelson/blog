package org.sfnelson.blog.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
@GinModules(BlogModule.class)
public interface Injector extends Ginjector {
	EventBus getEventBus();
	PlaceController getPlaceController();
	HistoryMapper getHistoryMapper();
	ShowBlog getBlog();
	ShowTasks getTasks();
	ShowAuth getAuth();
	AdminApp getAdminApp();
}
