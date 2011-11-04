package org.sfnelson.blog.server;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.mongodb.DBObject;
import org.sfnelson.blog.server.domain.Content;
import org.sfnelson.blog.server.domain.Entry;
import org.sfnelson.blog.server.domain.Update;
import org.sfnelson.blog.server.mongo.Database;
import org.sfnelson.blog.server.domain.Post;
import org.sfnelson.blog.server.mongo.DomainObject;
import org.sfnelson.blog.server.security.RequiresLogin;
import org.sfnelson.blog.server.service.ContentService;
import org.sfnelson.blog.server.service.EntryService;

import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
public class EntryManager implements EntryService, ContentService {

	private final Database database;
	private final Provider<Post> posts;
	private final Provider<Update> updates;

	@Inject
	EntryManager(Database database, Provider<Post> posts, Provider<Update> updates) {
		this.database = database;
		this.posts = posts;
		this.updates = updates;
	}

	@Override
	@RequiresLogin
	public void createEntry(Entry update) {
		if (update != null) {
			database.persist(update);
		}
	}

	@Override
	@RequiresLogin
	public void updateEntry(Entry update) {
		if (update != null) {
			database.update(update);
		}
	}

	@Override
	@RequiresLogin
	public void deleteEntry(Entry update) {
		database.remove(update);
	}

	@Override
	@RequiresLogin
	public void createContent(Content post) {
		if (post != null) {
			database.persist(post);
		}
	}

	@Override
	@RequiresLogin
	public void updateContent(Content post) {
		if (post != null) {
			database.update(post);
		}
	}

	@Override
	@RequiresLogin
	public void deleteContent(Content post) {
		if (post != null) {
			database.remove(post);
		}
	}

	public List<Post> getEntries() {
		List<Post> entries = Lists.newArrayList();
		for (DBObject entry: database.findPosts()) {
			entries.add(posts.get().init(entry));
		}
		return entries;
	}

	@Override
	public List<Entry> getEntries(int start, int limit) {
		List<Entry> entries = Lists.newArrayList();
		int count = 0;
		for (DBObject entry: database.findEntries().skip(start)) {
			if (entry.get("type").equals("post")) {
				entries.add(posts.get().init(entry));
			}
			else {
				entries.add(updates.get().init(entry));
			}
			if (++count >= limit) break;
		}
		return entries;
	}

	@Override
	public List<Post> getPosts(int start, int limit) {
		List<Post> entries = Lists.newArrayList();
		int count = 0;
		for (DBObject entry: database.findPosts().skip(start)) {
			entries.add(posts.get().init(entry));
			if (++count >= limit) break;
		}
		return entries;
	}
}
