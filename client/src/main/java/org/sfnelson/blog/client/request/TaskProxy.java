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
public interface TaskProxy extends EntityProxy {
	void setTitle(String title);
	String getTitle();
	void setDescription(String description);
	String getDescription();
	Date getCreated();
	void setCreated(Date date);
	Date getCompleted();
	void setCompleted(Date date);
	Date getUpdated();
	void setUpdated(Date date);
	Date getAbandoned();
	void setAbandoned(Date date);

	EntityProxyId<TaskProxy> stableId();
}
