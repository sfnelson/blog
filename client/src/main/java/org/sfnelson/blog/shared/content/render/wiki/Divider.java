package org.sfnelson.blog.shared.content.render.wiki;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 2/11/11
 */
public class Divider implements Element {
	@Override
	public SafeHtml parse(Input input) {
		return new SafeHtmlBuilder().appendHtmlConstant("<hr />\n").toSafeHtml();
	}
}
