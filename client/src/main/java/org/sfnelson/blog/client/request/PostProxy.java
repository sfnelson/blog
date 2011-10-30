package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.domain.Post;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
@ProxyFor(value = Post.class, locator = DomainObjectLocator.class)
public interface PostProxy extends EntryProxy {
	String getTitle();
	void setTitle(String title);

	EntityProxyId<PostProxy> stableId();
}
