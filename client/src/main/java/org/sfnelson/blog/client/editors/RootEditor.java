package org.sfnelson.blog.client.editors;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.EntityProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 5/11/11
 */
public interface RootEditor<T extends EntityProxy> extends Editor<T>, SelectableEditor {

	interface HasDelegates {
		void startEditing(IsWidget delegate);

		void editPrevious(IsWidget currentDelegate);

		void editNext(IsWidget currentDelegate);

		void doneEditing(IsWidget delegate);

		void cancelEditing(IsWidget delegate);
	}

	interface Initializer<T extends EntityProxy> {
		void initialize(T value);
	}

	<X extends T> X create(Class<X> type, Initializer<X> initializer);

	void init(T value);

}
