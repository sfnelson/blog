package org.sfnelson.blog.client.views;

import com.google.gwt.editor.client.IsEditor;

import org.sfnelson.blog.client.editors.RootEditor;
import org.sfnelson.blog.client.request.EntryProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public interface EntryView<T extends EntryProxy> extends ModalEditorView, IsEditor<RootEditor<T>> {
	interface EntryEditor<T extends EntryProxy> extends RootEditor<T> {
		void requestDelete();

		@Ignore
		EntryView<T> asWidget();
	}
}
