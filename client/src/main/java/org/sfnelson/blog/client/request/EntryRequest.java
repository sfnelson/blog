package org.sfnelson.blog.client.request;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ExtraTypes;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import org.sfnelson.blog.server.ServiceLocator;
import org.sfnelson.blog.server.service.EntryService;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
@Service(value = EntryService.class, locator = ServiceLocator.class)
@ExtraTypes({PostProxy.class, UpdateProxy.class})
public interface EntryRequest extends RequestContext {
	Request<List<EntryProxy>> getEntries(int start, int limit);

	Request<List<PostProxy>> getPosts(int start, int limit);

	Request<Void> createEntry(EntryProxy post);

	Request<Void> updateEntry(EntryProxy post);

	Request<Void> deleteEntry(EntryProxy post);

	Request<Void> createContent(ContentProxy post);

	Request<Void> updateContent(ContentProxy post);

	Request<Void> deleteContent(ContentProxy post);
}
