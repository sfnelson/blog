package org.sfnelson.blog.server;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import javax.validation.*;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.server.mongo.Database;
import org.sfnelson.blog.server.security.LoginChecker;
import org.sfnelson.blog.server.security.RequiresLogin;
import org.sfnelson.blog.server.service.ContentService;
import org.sfnelson.blog.server.service.EntryService;
import org.sfnelson.blog.server.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
				serve("/feed.xml").with(RssServlet.class);
				serve("/oauth").with(OAuthServlet.class);

				bind(ServiceLayerDecorator.class).to(InjectingServiceLayerDecorator.class);
				bind(Database.class).asEagerSingleton();

				bind(EntryService.class).to(EntryManager.class);
				bind(ContentService.class).to(EntryManager.class);
				bind(TaskService.class).to(TaskManager.class);
				bind(Locator.class).to(DomainObjectLocator.class);

				bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresLogin.class), new LoginChecker());

				System.setProperty("gwt.rpc.dumpPayload", "false");
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

			@Provides
			@Singleton
			public ExceptionHandler getExceptionHandler() {
				return new ExceptionHandler() {
					Logger logger = LoggerFactory.getLogger(RequestFactory.class);

					@Override
					public ServerFailure createServerFailure(Throwable throwable) {
						logger.error(throwable.getMessage(), throwable);
						return new ServerFailure(throwable.getMessage(), throwable.getClass().getName(), null, true);
					}
				};
			}
		});
	}
}
