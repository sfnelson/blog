package org.sfnelson.blog.server;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class ServiceLocator implements com.google.web.bindery.requestfactory.shared.ServiceLocator {

	private final com.google.inject.Injector injector;

	@Inject
	ServiceLocator(Injector injector) {
		this.injector = injector;
	}

	@Override
	public Object getInstance(Class<?> clazz) {
		return injector.getInstance(clazz);
	}
}
