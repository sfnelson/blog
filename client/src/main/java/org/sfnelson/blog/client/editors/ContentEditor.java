package org.sfnelson.blog.client.editors;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import org.sfnelson.blog.client.request.ContentProxy;
import org.sfnelson.blog.domain.Content;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 3/11/11
 */
public interface ContentEditor extends Editor<ContentProxy> {
	Editor<String> valueEditor();
	Editor<Content.Type> typeEditor();
}
