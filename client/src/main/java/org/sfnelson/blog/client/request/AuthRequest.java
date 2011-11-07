package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import org.sfnelson.blog.server.AuthManager;
import org.sfnelson.blog.server.ServiceLocator;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
@Service(value = AuthManager.class, locator = ServiceLocator.class)
public interface AuthRequest extends RequestContext {
	Request<AuthProxy> login(String provider, String returnURL);

	Request<AuthProxy> cookie(String authId);

	Request<AuthProxy> state();

	Request<AuthProxy> logout();
}
