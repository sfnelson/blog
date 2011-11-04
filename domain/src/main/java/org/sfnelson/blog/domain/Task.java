package org.sfnelson.blog.domain;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public interface Task {
	Author getOwner();
	String getTitle();
	Content getContent();
	Date getCreated();
	Date getLastUpdate();
	Date getCompleted();
	Date getAbandoned();
}
