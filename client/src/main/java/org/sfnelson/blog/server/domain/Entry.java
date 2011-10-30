package org.sfnelson.blog.server.domain;

import org.sfnelson.blog.server.mongo.DomainObject;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface Entry {
	Date getPosted();
	void setPosted(Date posted);
	String getContent();
	void setContent(String message);
}
