package org.sfnelson.blog.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.UmbrellaException;

import com.google.inject.Inject;
import org.sfnelson.blog.client.views.ErrorView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class ExceptionHandler implements GWT.UncaughtExceptionHandler {

	private final ErrorView view;

	@Inject
	ExceptionHandler(ErrorView view) {
		this.view = view;

		RootPanel.get().add(view);
	}

	@Override
	public void onUncaughtException(Throwable e) {
		e = getExceptionToDisplay(e);
		if (e.getMessage() != null && e.getMessage().length() > 0) {
			view.showError(e.getMessage());
		}
		GWT.log(e.getMessage(), e);
	}

	private Throwable getExceptionToDisplay(Throwable e) {
		while (true) {
			Throwable u = e;
			if (e instanceof UmbrellaException) {
				u = unpack((UmbrellaException) e);
			}
			if (u == e) break;
			else e = u;
		}
		return e;
	}

	private Throwable unpack(UmbrellaException e) {
		if (e.getCauses().size() == 1) {
			return e.getCauses().iterator().next();
		} else {
			String message = null;
			Throwable last = null;
			for (Throwable t : e.getCauses()) {
				if (message == null || message.equals(t.getMessage())) {
					message = t.getMessage();
					last = t;
				} else {
					return e; // different messages, return all.
				}
			}
			return last; // same messages, return last.
		}
	}
}
