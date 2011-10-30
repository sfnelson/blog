package org.sfnelson.blog.shared.domain;

/**
* Author: Stephen Nelson <stephen@sfnelson.org>
* Date: 31/10/11
*/
public enum TaskUpdateType {

	CREATED("CREATED"),
	PROGRESS("PROGRESS"),
	COMPLETED("COMPLETED"),
	ABANDONED("ABANDONED");

	private final String name;

	TaskUpdateType(String name) {
		this.name = name;
	}
}
