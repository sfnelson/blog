package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.domain.Entry;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
@ProxyFor(value = Entry.class, locator = DomainObjectLocator.class)
public interface EntryProxy extends EntityProxy {
	Date getPosted();
	void setPosted(Date date);
	String getContent();
	void setContent(String content);
}
