package org.sfnelson.blog.server.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.sfnelson.blog.server.RequestFactoryServlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class LoginChecker implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		HttpSession session = RequestFactoryServlet.getThreadLocalRequest().getSession();
		String oauthId = (String) session.getAttribute("oauth-id");
		if (oauthId != null) return methodInvocation.proceed();
		else throw new LoginRequiredException();
	}
}
