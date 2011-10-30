package org.sfnelson.blog.client.views;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.user.client.ui.IsWidget;
import org.sfnelson.blog.client.request.EntryProxy;
import org.sfnelson.blog.client.request.PostProxy;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public interface EntryView<T extends EntryProxy> extends ModalEditorView<T> {

	void setEditor(EntryEditor<T> editor);

	interface EntryEditor<T extends EntryProxy> extends Editor<T> {
		void requestDelete();
	}
}
