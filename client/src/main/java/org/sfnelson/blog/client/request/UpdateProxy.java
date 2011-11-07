package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.domain.Update;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
@ProxyFor(value = Update.class, locator = DomainObjectLocator.class)
public interface UpdateProxy extends EntryProxy, org.sfnelson.blog.domain.Update {
	TaskProxy getTask();

	void setTask(TaskProxy task);

	TaskUpdateType getType();

	void setType(TaskUpdateType type);

	ContentProxy getContent();

	AuthorProxy getAuthor();

	EntityProxyId<UpdateProxy> stableId();
}
