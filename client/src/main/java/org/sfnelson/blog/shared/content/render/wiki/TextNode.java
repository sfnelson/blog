package org.sfnelson.blog.shared.content.render.wiki;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import org.sfnelson.blog.shared.content.render.Input;

/**
* Author: Stephen Nelson <stephen@sfnelson.org>
* Date: 1/11/11
*/
public class TextNode implements Element {
	private Parent parent;

	public TextNode(Parent parent) {
		this.parent = parent;
	}

	@Override
	public SafeHtml parse(Input input) {
		SafeHtmlBuilder builder = new SafeHtmlBuilder();
		while (parent.checkTerminal(input)) {
			switch (input.current()) {
				case '*': return builder.toSafeHtml();
				case '_': return builder.toSafeHtml();
				case '`': return builder.toSafeHtml();
				case '{':
					if (input.peek(1) == '{' && input.peek(2) == '{')
						  return builder.toSafeHtml();
					else break;
				case '^': return builder.toSafeHtml();
				case ',':
					if (input.peek(1) == ',')
						  return builder.toSafeHtml();
					else break;
				case '~':
					if (input.peek(1) == '~')
						  return builder.toSafeHtml();
					else break;
			}
			builder.append(input.current());
			input.forward();
		}
		return builder.toSafeHtml();
	}
}
