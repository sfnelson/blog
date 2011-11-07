package org.sfnelson.blog.client.widgets;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.FocusImpl;

import java.util.Iterator;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 28/10/11
 */
public class ArticlePanel extends ComplexPanel implements Focusable, HasFocusHandlers, HasBlurHandlers {

	public ArticlePanel() {
		Element article = DOM.createElement("ARTICLE");
		article.setTabIndex(-1);
		setElement(article);
	}

	@Override
	public void add(Widget widget) {
		add(widget, getElement());
	}

	@Override
	public int getTabIndex() {
		return getElement().getTabIndex();
	}

	@Override
	public void setAccessKey(char key) {
		throw new UnsupportedOperationException("access key not supported");
	}

	@Override
	public void setFocus(boolean focused) {
		if (focused) getElement().focus();
		else getElement().blur();
	}

	@Override
	public void setTabIndex(int index) {
		getElement().setTabIndex(index);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return addDomHandler(handler, BlurEvent.getType());
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return addDomHandler(handler, FocusEvent.getType());
	}
}
