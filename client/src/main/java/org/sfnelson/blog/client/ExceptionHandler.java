package org.sfnelson.blog.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.*;
import com.google.web.bindery.event.shared.UmbrellaException;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class ExceptionHandler implements GWT.UncaughtExceptionHandler {
	@Override
	public void onUncaughtException(Throwable e) {
		e = getExceptionToDisplay(e);
		Window.alert(e.getMessage());
	}

	private Throwable getExceptionToDisplay(Throwable e) {
		while (e instanceof UmbrellaException) {
			if (((UmbrellaException) e).getCauses().size() == 1) {
				e = ((UmbrellaException) e).getCauses().iterator().next();
			}
			else {
				break;
			}
		}
		return e;
	}
}
