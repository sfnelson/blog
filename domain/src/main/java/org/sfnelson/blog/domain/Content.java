package org.sfnelson.blog.domain;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public interface Content {
	enum Type {
		HTML, TEXT, WIKI;
	}

	Type getType();
	String getValue();
}
