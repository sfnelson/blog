package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import org.sfnelson.blog.server.Auth;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
@ProxyFor(Auth.class)
public interface AuthProxy extends ValueProxy {
	String getRedirectURL();
	String getEmail();
	boolean getAuthenticated();
	boolean getAuthor();
}
