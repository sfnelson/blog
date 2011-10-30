package org.sfnelson.blog.server;

import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;

import javax.inject.Singleton;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
@Singleton
public class RequestFactoryServlet extends com.google.web.bindery.requestfactory.server.RequestFactoryServlet {

	@Inject
	RequestFactoryServlet(ExceptionHandler exceptionHandler, ServiceLayerDecorator serviceLayerDecorator) {
		super(exceptionHandler, serviceLayerDecorator);
	}

}
