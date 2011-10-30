package org.sfnelson.blog.client.views;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.EntityProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface ModalEditorView<T extends EntityProxy>
		extends IsEditor<com.google.gwt.editor.client.Editor<T>>, IsWidget {

	void edit();
	void view();
	void select();
	void deselect();

	interface Editor<T extends EntityProxy> extends com.google.gwt.editor.client.Editor<T> {
		void requestEdit();
		void requestSubmit();
		void requestCancel();
		void requestSelect();
	}
}
