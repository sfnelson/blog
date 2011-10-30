package org.sfnelson.blog.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import org.sfnelson.blog.client.widgets.RootPanel;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 14/10/11
 */
public class BlogEntryPoint implements EntryPoint {

	private final Injector injector = GWT.create(Injector.class);

	@Override
	public void onModuleLoad() {
		EventBus eventBus = injector.getEventBus();

		injector.getBlog().start(new RootPanel("entries"), eventBus);
	}
}
