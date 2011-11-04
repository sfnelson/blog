package org.sfnelson.blog.client.editors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import org.sfnelson.blog.client.request.ContentProxy;
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.views.PostView;
import org.sfnelson.blog.domain.Content;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 28/10/11
 */
public class PostEditor extends EntryEditor<PostProxy, PostView> {

	interface Driver extends RequestFactoryEditorDriver<PostProxy, PostEditor> {};

	private final Driver driver;

	@Inject
	PostEditor(PostView view, RequestFactory rf, Provider<EntryRequest> request, EventBus eventBus) {
		super(view, request, eventBus, PostProxy.class);

		driver = GWT.<Driver> create(Driver.class);
		driver.initialize(rf, this);
	}

	Editor<String> title() {
		return getView().getTitleEditor();
	}

	Editor<Date> posted() {
		return getView().getPostedEditor();
	}

	ContentEditor content() {
		return getView().getContentEditor();
	}

	public PostProxy create() {
		EntryRequest context = edit();
		PostProxy value = context.create(PostProxy.class);
		value.setTitle(DateTimeFormat.getFormat("EEEE").format(new Date()));
		value.setPosted(new Date());
		ContentProxy content = context.create(ContentProxy.class);
		content.setType(Content.Type.WIKI);
		value.setContent(content);
		context.createContent(content);
		context.createEntry(value);
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				requestSelect();
				getView().focus();
			}
		});
		return create(value, context);
	}

	@Override
	protected RequestFactoryEditorDriver<PostProxy, ? extends EntryEditor<PostProxy, ?>> getDriver() {
		return driver;
	}
}
