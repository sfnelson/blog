package org.sfnelson.blog.client.views;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.Event;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public interface NavigationView<T> extends IsWidget {

	void setPresenter(Presenter<T> presenter);
	void clear();
	void addTarget(T target, String title);

	interface Presenter<T> {
		void fire(T target);
	}
}
