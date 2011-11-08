package org.sfnelson.blog.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

import com.google.common.collect.Lists;
import org.sfnelson.blog.client.editors.SelectableEditor;
import org.sfnelson.blog.client.ioc.Injector;
import org.sfnelson.blog.client.places.BlogPlace;
import org.sfnelson.blog.client.widgets.RootPanel;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 14/10/11
 */
public class BlogEntryPoint implements EntryPoint {

	private final Injector injector = GWT.create(Injector.class);

	@Override
	public void onModuleLoad() {
		injector.getResources().style().ensureInjected();
		GWT.setUncaughtExceptionHandler(injector.getExceptionHandler());

		EventBus eventBus = injector.getEventBus();

		new ActivityManager(injector.getAuth(), eventBus).setDisplay(new RootPanel("auth"));
		new ActivityManager(injector.getNav(), eventBus).setDisplay(new RootPanel("navigation"));
		new ActivityManager(injector.getEntries(), eventBus).setDisplay(new RootPanel("entries"));
		new ActivityManager(injector.getTasks(), eventBus).setDisplay(new RootPanel("tasks"));

		PlaceController pc = injector.getPlaceController();
		HistoryMapper hm = injector.getHistoryMapper();

		PlaceHistoryHandler hh = new PlaceHistoryHandler(hm);
		hh.register(pc, (com.google.web.bindery.event.shared.EventBus) eventBus, BlogPlace.PLACE);

		hh.handleCurrentHistory();

		new SelectionManager(new EditorMapper() {
			private java.util.List<EditorMapper> mappers = Lists.<EditorMapper>newArrayList(
					injector.getEntries(),
					injector.getTasks());

			@Override
			public SelectableEditor getEditor(EntityProxyId<?> entity) {
				for (EditorMapper m : mappers) {
					SelectableEditor editor = m.getEditor(entity);
					if (editor != null) return editor;
				}
				return null;
			}

			@Override
			public EntityProxyId<?> getPrevious(EntityProxyId<?> current) {
				for (EditorMapper m : mappers) {
					EntityProxyId<?> prev = m.getPrevious(current);
					if (prev != null) return prev;
				}
				return null;
			}

			@Override
			public EntityProxyId<?> getNext(EntityProxyId<?> current) {
				for (EditorMapper m : mappers) {
					EntityProxyId<?> next = m.getNext(current);
					if (next != null) return next;
				}
				return null;
			}
		}).register(eventBus);
	}
}
