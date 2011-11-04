package org.sfnelson.blog.server.security;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class LoginRequiredException extends RuntimeException {
	public LoginRequiredException() {
		super("Login Required");
	}
}
