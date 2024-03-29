package org.sfnelson.blog.client.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.places.AuthPlace;
import org.sfnelson.blog.client.places.AuthorPlace;
import org.sfnelson.blog.client.places.BlogPlace;
import org.sfnelson.blog.client.request.AuthProxy;
import org.sfnelson.blog.client.request.AuthRequest;
import org.sfnelson.blog.client.ui.AuthWidget;
import org.sfnelson.blog.client.views.AuthView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
public class ShowAuth extends AbstractActivity implements AuthWidget.Presenter {

	private final Provider<AuthRequest> request;
	private final AuthView view;
	private final PlaceController pc;

	private Place current;
	private AuthProxy credentials;

	@Inject
	ShowAuth(Provider<AuthRequest> request, AuthView view, PlaceController pc) {
		this.request = request;
		this.view = view;
		this.pc = pc;
	}

	public ShowAuth goTo(Place place) {
		this.current = place;
		checkPlace();
		return this;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.setPresenter(this);
		panel.setWidget(view);

		request.get().state().fire(new Receiver());
	}

	@Override
	public void login(String value) {
		request.get().login(value, Window.Location.getHref()).fire(new Receiver());
	}

	@Override
	public void update() {
		request.get().state().fire(new Receiver());
	}

	@Override
	public void logout() {
		request.get().logout().fire(new Receiver());
	}

	private void checkPlace() {
		if (current != null && credentials != null) {
			if (current instanceof AuthPlace) {
				if (((AuthPlace) current).getAction().equals("ready")) {
					update();
					view.hideDialog();
				}
			}

			if (credentials.getAuthor()) {
				if (!(current instanceof AuthorPlace)) {
					pc.goTo(new AuthorPlace(""));
				}
			} else {
				if (!(current instanceof BlogPlace)) {
					pc.goTo(BlogPlace.PLACE);
				}
			}
		}
	}

	private class Receiver extends com.google.web.bindery.requestfactory.shared.Receiver<AuthProxy> {
		@Override
		public void onSuccess(AuthProxy response) {
			if (!response.getAuthenticated()) {
				view.setLogin();
				if (response.getRedirectURL() != null) {
					view.showDialog(response.getRedirectURL());
				}
			} else {
				view.setEmail(response.getEmail());
			}

			ShowAuth.this.credentials = response;
			checkPlace();
		}
	}
}
