package org.sfnelson.blog.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.web.bindery.requestfactory.server.DefaultExceptionHandler;
import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import org.sfnelson.blog.server.mongo.Database;
import org.sfnelson.blog.server.service.EntryService;
import org.sfnelson.blog.server.service.TaskService;

import javax.validation.*;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class ServletConfig extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServletModule() {
			@Override
			protected void configureServlets() {
				serve("/gwtRequest").with(RequestFactoryServlet.class);
				serve("/feed").with(RssServlet.class);
				serve("/oauth").with(OAuthServlet.class);

				bind(ExceptionHandler.class).to(DefaultExceptionHandler.class);
				bind(ServiceLayerDecorator.class).to(InjectingServiceLayerDecorator.class);
				bind(Database.class).asEagerSingleton();

				bind(EntryService.class).to(EntryManager.class).in(Singleton.class);
				bind(TaskService.class).to(TaskManager.class).in(Singleton.class);
			}

			@Provides
			@Singleton
			public ValidatorFactory getValidatorFactory(ConstraintValidatorFactory cf) {
				return Validation.byDefaultProvider().configure()
						.constraintValidatorFactory(cf)
						.buildValidatorFactory();
			}

			@Provides
			@Singleton
			public Validator getValidator(ValidatorFactory factory) {
				return factory.getValidator();
			}

			@Provides
			@Singleton
			public ConstraintValidatorFactory getConstraintValidatorFactory(final Injector injector) {
				return new ConstraintValidatorFactory() {
					@Override
					public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
						return injector.getInstance(key);
					}
				};
			}
		});
	}
}
