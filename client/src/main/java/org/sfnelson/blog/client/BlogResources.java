package org.sfnelson.blog.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 6/11/11
 */
public interface BlogResources extends ClientBundle {
	@Source("blog.css")
	Style style();

	interface Style extends CssResource {
		String error();

		String errorMessage();

		String show();
	}
}
