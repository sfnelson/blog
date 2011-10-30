package org.sfnelson.blog.client.request;

import com.google.web.bindery.requestfactory.shared.ExtraTypes;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import org.sfnelson.blog.server.ServiceLocator;
import org.sfnelson.blog.server.service.EntryService;

import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
@Service(value = EntryService.class, locator = ServiceLocator.class)
@ExtraTypes({PostProxy.class, TaskUpdateProxy.class})
public interface EntryRequest extends RequestContext {
	Request<List<EntryProxy>> getEntries(int start, int limit);
	Request<List<PostProxy>> getPosts(int start, int limit);

	Request<Void> create(EntryProxy post);
	Request<Void> update(EntryProxy post);
	Request<Void> delete(EntryProxy post);
}
