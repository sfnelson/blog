package org.sfnelson.blog.client.widgets;

import com.google.web.bindery.event.shared.EventBus;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.ioc.Author;
import org.sfnelson.blog.client.views.PostView;
import org.sfnelson.blog.client.views.UpdateView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class EditableEntryList extends EntryList {

	@Inject
	EditableEntryList(EventBus eventBus, @Author Provider<PostView> posts, @Author Provider<UpdateView> updates) {
		super(eventBus, posts, updates);
	}

}
