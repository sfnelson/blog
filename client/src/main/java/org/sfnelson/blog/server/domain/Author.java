package org.sfnelson.blog.server.domain;

import com.google.inject.Inject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.mongo.DomainObject;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class Author extends DomainObject<Author> implements org.sfnelson.blog.domain.Author {

	@Inject
	Author() {
		super();
	}

	@Override
	public ObjectId getId() {
		return (ObjectId) super.getId();
	}

	@Override
	public String getDisplayName() {
		return (String) get("displayName");
	}

	public void setDisplayName(String displayName) {
		delta.put("displayName", displayName);
	}

	@Override
	public String getUsername() {
		return (String) get("username");
	}

	public void setUsername(String username) {
		delta.put("username", username);
	}

	public String getOpenId() {
		return (String) get("openid");
	}

	public void setOpenId(String openId) {
		delta.put("openid", openId);
	}

	public String getEmail() {
		return (String) get("email");
	}

	public void setEmail(String email) {
		delta.put("email", email);
	}

}
