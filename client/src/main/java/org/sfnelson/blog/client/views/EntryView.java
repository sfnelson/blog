package org.sfnelson.blog.client.views;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.user.client.ui.IsWidget;
import org.sfnelson.blog.client.request.PostProxy;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public interface EntryView extends IsEditor<Editor<PostProxy>>, IsWidget {

	void setPresenter(Presenter presenter);

	void edit();
	void view();
	void select();
	void deselect();

	Editor<String> getTitleEditor();
	Editor<Date> getPostedEditor();
	Editor<String> getContentEditor();

	interface Presenter extends ValueAwareEditor<PostProxy> {
		void requestSelect();
		void requestEdit();
		void requestSubmit();
		void requestCancel();
		void requestDelete();
	}
}
