package org.sfnelson.blog.server.security;

import com.google.inject.Provider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.sfnelson.blog.server.AuthManager;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class LoginChecker implements MethodInterceptor {

	private final Provider<AuthManager> authManager;

	public LoginChecker(Provider<AuthManager> authManager) {
		this.authManager = authManager;
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (authManager.get().state().getAuthenticated()) {
			return methodInvocation.proceed();
		} else {
			throw new LoginRequiredException();
		}
	}
}
