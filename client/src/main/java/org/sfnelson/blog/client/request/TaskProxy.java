package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.domain.Task;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
@ProxyFor(value = Task.class, locator = DomainObjectLocator.class)
public interface TaskProxy extends EntityProxy, org.sfnelson.blog.domain.Task {
	void setTitle(String title);
	void setContent(ContentProxy content);
	ContentProxy getContent();
	AuthorProxy getOwner();
	void setCreated(Date date);
	void setCompleted(Date date);
	void setLastUpdate(Date date);
	void setAbandoned(Date date);

	EntityProxyId<TaskProxy> stableId();
}
