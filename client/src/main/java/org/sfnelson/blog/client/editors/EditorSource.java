package org.sfnelson.blog.client.editors;

import com.google.gwt.editor.client.Editor;
import com.google.web.bindery.requestfactory.shared.EntityProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 5/11/11
 */
public interface EditorSource<T extends EntityProxy> {

	public <X extends T> RootEditor<X> create(int index, Class<X> type);
	public void dispose(Object o);

}
