package org.sfnelson.blog.server;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
public class Auth {
	private String email;
	private String redirectURL;
	private boolean isAuthenticated;
	private boolean isAuthor;

	public Auth() {}

	public Auth(String redirectURL) {
		this.redirectURL = redirectURL;
		this.isAuthenticated = false;
	}

	public Auth(String email, boolean isAuthor) {
		this.email = email;
		this.isAuthor = isAuthor;
		this.isAuthenticated = true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public boolean getAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.isAuthenticated = authenticated;
	}

	public boolean getAuthor() {
		return isAuthor;
	}

	public void setAuthor(boolean author) {
		this.isAuthor = author;
	}
}
