package org.sfnelson.blog.client;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.places.AuthPlace;
import org.sfnelson.blog.client.places.ManagerPlace;
import org.sfnelson.blog.client.request.AuthProxy;
import org.sfnelson.blog.client.request.AuthRequest;
import org.sfnelson.blog.client.ui.AuthWidget;
import org.sfnelson.blog.client.views.AuthView;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
public class ShowAuth extends AbstractActivity implements ActivityMapper, AuthWidget.Presenter {

	private final Provider<AuthRequest> request;
	private final AuthView view;
	private final PlaceController pc;

	private AuthPlace current;

	@Inject
	ShowAuth(Provider<AuthRequest> request, AuthView view, PlaceController pc) {
		this.request = request;
		this.view = view;
		this.pc = pc;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AuthPlace) {
			this.current = (AuthPlace) place;
			if (current.getAction().equals("ready")) {
				update();
				view.hideDialog();
			}
		}
		return this;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.setPresenter(this);
		panel.setWidget(view);

		String oauthId = Cookies.getCookie("oauth-id");
		if (oauthId != null) {
			request.get().cookie(oauthId).fire(new Receiver());
		}
		else {
			request.get().state().fire(new Receiver());
		}
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
		Cookies.removeCookie("oauth-id");
		request.get().logout().fire(new Receiver());
	}

	private class Receiver extends com.google.web.bindery.requestfactory.shared.Receiver<AuthProxy> {
		@Override
		public void onSuccess(AuthProxy response) {
			if (current != null) {
				// we've received our auth response, update the place.
				pc.goTo(ManagerPlace.PLACE);
			}
			if (!response.getAuthenticated()) {
				view.setLogin();
				if (response.getRedirectURL() != null) {
					view.showDialog(response.getRedirectURL());
				}
			}
			else {
				view.setEmail(response.getEmail());
				Date expires = new Date();
				expires.setTime(expires.getTime() + 14 * 24 * 60 * 60 * 1000); // two weeks.
				Cookies.setCookie("oauth-id", response.getAuthId(), expires);
			}
		}
	}
}
