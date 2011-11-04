package org.sfnelson.blog.domain;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public interface Comment {
	Author getAuthor();
	Date getPosted();
	Content getContent();
}
