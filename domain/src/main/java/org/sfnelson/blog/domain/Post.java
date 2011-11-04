package org.sfnelson.blog.domain;

import java.util.Date;
import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public interface Post extends Entry {
	String getTitle();
	Date getPosted();
}
