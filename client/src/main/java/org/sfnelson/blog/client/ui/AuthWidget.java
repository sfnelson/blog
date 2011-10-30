package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import org.sfnelson.blog.client.views.AuthView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
public class AuthWidget extends Composite implements AuthView {

	interface Binder extends UiBinder<FlowPanel, AuthWidget> {}
	interface Style extends CssResource {
		String close();
		String hidden();
	}

	@UiField Style style;
	@UiField Label login;
	@UiField Label email;
	@UiField DialogBox box;
	@UiField Anchor close;

	@UiField Anchor google;
	@UiField Anchor yahoo;
	@UiField Anchor other;

	private Presenter presenter;

	private JavaScriptObject authWindow;

	@Inject
	AuthWidget() {
		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));

		box.addStyleName(style.hidden());
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	public void setLogin() {
		login.removeStyleName(style.hidden());
		email.addStyleName(style.hidden());
	}

	@UiHandler("login")
	void doLogin(ClickEvent ev) {
		box.removeStyleName(style.hidden());
		box.center();
		box.show();
	}

	public void setEmail(String address) {
		login.addStyleName(style.hidden());
		email.removeStyleName(style.hidden());
		email.setText(address);
	}

	@UiHandler("email")
	void doLogout(ClickEvent ev) {
		presenter.logout();
	}

	public void showDialog(String url) {
		if (authWindow != null) {
			authWindow = setAuthLocation(url);
		}
	}

	public void hideDialog() {
		if (authWindow != null) {
			authWindow = closeAuthWindow(authWindow);
		}
	}

	@UiHandler("google")
	void google(ClickEvent ev) {
		presenter.login("https://www.google.com/accounts/o8/id");
		if (authWindow == null) {
			authWindow = createAuthWindow();
			close(null);
		}
	}

	@UiHandler("yahoo")
	void yahoo(ClickEvent ev) {
		presenter.login("https://me.yahoo.com");
		if (authWindow == null) {
			authWindow = createAuthWindow();
			close(null);
		}
	}

	@UiHandler("other")
	void other(ClickEvent ev) {
		String value = Window.prompt("Enter your OpenId:", "https://www.google.com/accounts/o8/id");
		if (value != null && value.length() > 0) {
			presenter.login(value);
			if (authWindow == null) {
				authWindow = createAuthWindow();
				close(null);
			}
		}
	}

	@UiHandler("close")
	void close(ClickEvent ev) {
		box.hide();
		box.addStyleName(style.hidden());
	}

	private native JavaScriptObject createAuthWindow() /*-{
		return $wnd.open("about:blank", "OAuth", "location=0,status=0,width=600,height=400");
	}-*/;

	private native JavaScriptObject setAuthLocation(String url) /*-{
		var window = $wnd.open(url, "OAuth", "location=0,status=0,width=600,height=400");
		window.focus();
		return window;
	}-*/;

	private native JavaScriptObject closeAuthWindow(JavaScriptObject authWindow) /*-{
		authWindow.close();
		return null;
	}-*/;
}
