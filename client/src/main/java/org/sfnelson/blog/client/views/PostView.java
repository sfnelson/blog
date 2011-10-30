package org.sfnelson.blog.client.views;

import com.google.gwt.editor.client.Editor;
import org.sfnelson.blog.client.request.PostProxy;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public interface PostView extends EntryView<PostProxy> {
	com.google.gwt.editor.client.Editor<String> getTitleEditor();
	com.google.gwt.editor.client.Editor<Date> getPostedEditor();
	com.google.gwt.editor.client.Editor<String> getContentEditor();
	void focus();
}
