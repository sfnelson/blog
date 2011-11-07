package org.sfnelson.blog.server.domain;

import com.google.inject.Inject;
import org.sfnelson.blog.server.mongo.DomainObject;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public class Content extends DomainObject<Content> implements org.sfnelson.blog.domain.Content {

	@Inject
	Content() {
	}

	@Override
	public Type getType() {
		String type = (String) get("type");
		if (type == null) return Type.WIKI;
		return Type.valueOf(type);
	}

	public void setType(Type type) {
		delta.put("type", type.name());
	}

	@Override
	public String getValue() {
		return (String) get("value");
	}

	public void setValue(String value) {
		delta.put("value", value);
	}
}
