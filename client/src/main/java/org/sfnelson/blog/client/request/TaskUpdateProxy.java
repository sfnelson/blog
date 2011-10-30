package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.domain.TaskUpdate;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
@ProxyFor(value = TaskUpdate.class, locator = DomainObjectLocator.class)
public interface TaskUpdateProxy extends EntryProxy {
	TaskProxy getTask();
	void setTask(TaskProxy task);
	TaskUpdateType getType();
	void setType(TaskUpdateType type);

	EntityProxyId<TaskUpdateProxy> stableId();
}
