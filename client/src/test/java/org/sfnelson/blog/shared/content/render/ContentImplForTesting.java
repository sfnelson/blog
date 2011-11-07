package org.sfnelson.blog.shared.content.render;

import org.sfnelson.blog.domain.Content;

/**
* Author: Stephen Nelson <stephen@sfnelson.org>
* Date: 2/11/11
*/
class ContentImplForTesting implements Content {
	private String input;
	private Type type;

	public ContentImplForTesting(String input, Type type) {
		this.input = input;
		this.type = type;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getValue() {
		return input;
	}
}
