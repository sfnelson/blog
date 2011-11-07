package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.domain.Author;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
@ProxyFor(value = Author.class, locator = DomainObjectLocator.class)
public interface AuthorProxy extends EntityProxy, org.sfnelson.blog.domain.Author {
	@Override
	EntityProxyId<AuthorProxy> stableId();
}
