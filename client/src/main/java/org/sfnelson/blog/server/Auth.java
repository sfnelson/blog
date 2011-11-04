package org.sfnelson.blog.server;

import com.google.inject.Inject;
import org.sfnelson.blog.server.mongo.DomainObject;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
public class Auth extends DomainObject<Auth> {

	private final String redirectURL;

	@Inject
	Auth() {
		redirectURL = null;
	}

	public Auth(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getAuthId() {
		return (String) get("_id");
	}

	public void setAuthId(String authId) {
		delta.put("_id", authId);
	}

	public String getEmail() {
		return (String) get("email");
	}

	public void setEmail(String email) {
		delta.put("email", email);
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public boolean getAuthenticated() {
		return getAuthId() != null;
	}

	public boolean getAuthor() {
		Boolean author = (Boolean) get("author");
		if (author == null) return false;
		return author;
	}

	public void setAuthor(boolean author) {
		delta.put("author", true);
	}
}
