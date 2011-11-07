package org.sfnelson.blog.client.views;

import org.sfnelson.blog.client.request.PostProxy;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface PostView extends EntryView<PostProxy> {
	void focus();
}
