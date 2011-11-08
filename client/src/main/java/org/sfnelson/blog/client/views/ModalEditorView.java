package org.sfnelson.blog.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.EntityProxy;

import org.sfnelson.blog.client.editors.RootEditor;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface ModalEditorView extends IsWidget {

	void focus();

	void edit();

	void view();

	boolean select();

	boolean deselect();

	interface Editor<T extends EntityProxy> extends RootEditor<T> {
		void requestEdit();

		void requestSubmit();

		void requestCancel();

		void requestSelect();
	}
}
