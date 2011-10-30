package org.sfnelson.blog.server;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.security.Provider;
import java.util.Set;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class InjectingServiceLayerDecorator extends ServiceLayerDecorator {

	private final Injector injector;
	private final Validator validator;

	@Inject
	InjectingServiceLayerDecorator(Injector injector, Validator validator) {
		this.injector = injector;
		this.validator = validator;
	}

	@Override
	public <T extends Locator<?, ?>> T createLocator(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

	@Override
	public Object createServiceInstance(Class<? extends RequestContext> requestContext) {
		Class<? extends ServiceLocator> serviceLocator = getTop().resolveServiceLocator(requestContext);

		if (serviceLocator == null) return null;

		return injector.getInstance(serviceLocator)
					.getInstance(requestContext.getAnnotation(Service.class).value());
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validate(T domainObject) {
		return validator.validate(domainObject);
	}
}
