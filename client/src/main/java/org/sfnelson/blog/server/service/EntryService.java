package org.sfnelson.blog.server.service;

import org.sfnelson.blog.server.domain.Entry;
import org.sfnelson.blog.server.domain.Post;
import org.sfnelson.blog.server.domain.TaskUpdate;

import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface EntryService {
	List<Entry> getEntries(int start, int limit);
	List<Post> getPosts(int start, int limit);

	void create(Entry post);
	void update(Entry post);
	void delete(Entry post);
}
