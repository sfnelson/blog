package org.sfnelson.blog.client.views;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 30/10/11
 */
public interface AuthView extends IsWidget {

	void setPresenter(Presenter presenter);
	void setLogin();
	void setEmail(String address);
	void showDialog(String url);
	void hideDialog();

	interface Presenter {
		void login(String value);
		void update();
		void logout();
	}
}
