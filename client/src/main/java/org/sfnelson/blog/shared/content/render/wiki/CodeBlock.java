package org.sfnelson.blog.shared.content.render.wiki;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 2/11/11
 */
public class CodeBlock implements Element {
	@Override
	public SafeHtml parse(Input input) {
		SafeHtmlBuilder builder = new SafeHtmlBuilder();
		builder.append(input.annotateOpen("code"));
		while (!input.done()) {
			switch (input.current()) {
				case '\n':
					if (input.peek(1) == '}' && input.peek(2) == '}' && input.peek(3) == '}') {
						input.forward();
						input.forward();
						input.forward();
						input.forward();
						break;
					}
				default:
					builder.append(input.current());
					input.forward();
					continue;
			}
			break;
		}
		builder.append(input.annotateClose("code"));
		return builder.toSafeHtml();
	}
}
