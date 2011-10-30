package org.sfnelson.blog.server;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.domain.Entry;
import org.sfnelson.blog.server.domain.TaskUpdate;
import org.sfnelson.blog.server.mongo.Database;
import org.sfnelson.blog.server.domain.Post;
import org.sfnelson.blog.server.service.EntryService;

import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
public class EntryManager implements EntryService {

	private final Database database;
	private final DomainObjectLocator locator;

	@Inject
	EntryManager(Database database, DomainObjectLocator locator) {
		this.database = database;
		this.locator = locator;
	}

	@Override
	public void create(Entry update) {
		database.persist(update);
	}

	@Override
	public void update(Entry update) {
		database.update(update);
	}

	@Override
	public void delete(Entry update) {
		database.remove(update);
	}

	public List<Post> getEntries() {
		List<Post> entries = Lists.newArrayList();
		for (DBObject entry: database.findPosts()) {
			entries.add(new Post(entry, locator));
		}
		return entries;
	}

	@Override
	public List<Entry> getEntries(int start, int limit) {
		List<Entry> entries = Lists.newArrayList();
		int count = 0;
		for (DBObject entry: database.findEntries().skip(start)) {
			if (entry.get("type").equals("post")) {
				entries.add(new Post(entry, locator));
			}
			else {
				entries.add(new TaskUpdate(entry, locator));
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
			entries.add(new Post(entry, locator));
			if (++count >= limit) break;
		}
		return entries;
	}
}
