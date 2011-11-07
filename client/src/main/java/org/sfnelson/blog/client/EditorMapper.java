package org.sfnelson.blog.client;

import com.google.web.bindery.requestfactory.shared.EntityProxyId;

import org.sfnelson.blog.client.editors.SelectableEditor;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 6/11/11
 */
public interface EditorMapper {

	EntityProxyId<?> getPrevious(EntityProxyId<?> current);

	SelectableEditor getEditor(EntityProxyId<?> entity);

	EntityProxyId<?> getNext(EntityProxyId<?> current);

}
