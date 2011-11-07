package org.sfnelson.blog.client.request;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 14/10/11
 */
public interface RequestFactory extends com.google.web.bindery.requestfactory.shared.RequestFactory {
	TaskRequest taskRequest();

	EntryRequest entryRequest();

	AuthRequest authRequest();
}
