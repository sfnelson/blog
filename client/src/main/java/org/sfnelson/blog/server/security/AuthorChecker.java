package org.sfnelson.blog.server.security;

import com.google.inject.Provider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.sfnelson.blog.server.AuthManager;
import org.sfnelson.blog.server.domain.Auth;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 9/11/11
 */
public class AuthorChecker implements MethodInterceptor {

	private final Provider<AuthManager> authManager;

	public AuthorChecker(Provider<AuthManager> authManager) {
		this.authManager = authManager;
	}

	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
		Auth auth = authManager.get().state();
		if (auth.getAuthenticated() && auth.getAuthor()) {
			return method.proceed();
		} else if (auth.getAuthenticated()) {
			throw new AuthorRequiredException();
		} else {
			throw new LoginRequiredException();
		}
	}
}
