package org.sfnelson.blog.client.request;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.domain.Entry;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
@ProxyFor(value = Entry.class, locator = DomainObjectLocator.class)
public interface EntryProxy extends EntityProxy, org.sfnelson.blog.domain.Entry {
	AuthorProxy getAuthor();

	void setAuthor(AuthorProxy author);

	Date getPosted();

	void setPosted(Date date);

	ContentProxy getContent();

	void setContent(ContentProxy content);
}
