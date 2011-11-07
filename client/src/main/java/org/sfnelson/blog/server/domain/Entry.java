package org.sfnelson.blog.server.domain;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface Entry extends org.sfnelson.blog.domain.Entry {
	Date getPosted();

	void setPosted(Date posted);

	Author getAuthor();

	void setAuthor(Author author);

	Content getContent();

	void setContent(Content message);
}
