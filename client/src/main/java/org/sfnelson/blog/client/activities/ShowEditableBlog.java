package org.sfnelson.blog.client.activities;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.ioc.Author;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.views.BlogView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class ShowEditableBlog extends ShowBlog {

	@Inject
	ShowEditableBlog(@Author BlogView view, EventBus eventBus, Provider<EntryRequest> request) {
		super(view, eventBus, request);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
	}
}
