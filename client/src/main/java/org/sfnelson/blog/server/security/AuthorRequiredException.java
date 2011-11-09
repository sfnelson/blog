package org.sfnelson.blog.server.security;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 9/11/11
 */
public class AuthorRequiredException extends RuntimeException {
	public AuthorRequiredException() {
		super("Author permissions required");
	}
}
