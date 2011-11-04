package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.domain.Content;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
@ProxyFor(value = Content.class, locator = DomainObjectLocator.class)
public interface ContentProxy extends EntityProxy, org.sfnelson.blog.domain.Content {
	void setType(Type type);
	void setValue(String value);
	@Override
	EntityProxyId<ContentProxy> stableId();
}
