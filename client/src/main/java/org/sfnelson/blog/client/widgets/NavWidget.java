package org.sfnelson.blog.client.widgets;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;

import org.sfnelson.blog.client.views.NavigationView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public class NavWidget<T> extends ComplexPanel implements NavigationView<T> {

	private Presenter<T> presenter;

	public NavWidget() {
		setElement(DOM.createElement("UL"));
	}

	public void setPresenter(Presenter<T> presenter) {
		clear();

		this.presenter = presenter;
	}

	@Override
	public void addTarget(T target, String title) {
		add(new Task(target, title), getElement());
	}

	private class Task extends ComplexPanel implements ClickHandler {

		final Anchor anchor = new Anchor();
		final T target;

		public Task(T target, String title) {
			this.target = target;
			setElement(DOM.createElement("LI"));
			add(anchor, getElement());
			anchor.setText(title);
			anchor.addClickHandler(this);
		}

		public void onClick(ClickEvent ev) {
			if (ev.getNativeButton() == NativeEvent.BUTTON_LEFT) {
				presenter.fire(target);
				ev.stopPropagation();
				ev.preventDefault();
			}
		}
	}
}