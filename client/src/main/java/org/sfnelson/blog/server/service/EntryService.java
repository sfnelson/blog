package org.sfnelson.blog.server.service;

import java.util.List;

import org.sfnelson.blog.server.domain.Content;
import org.sfnelson.blog.server.domain.Entry;
import org.sfnelson.blog.server.domain.Post;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface EntryService {
	List<Entry> getEntries(int start, int limit);

	List<Post> getPosts(int start, int limit);

	void createEntry(Entry post);

	void updateEntry(Entry post);

	void deleteEntry(Entry post);

	void createContent(Content post);

	void updateContent(Content post);

	void deleteContent(Content post);
}

