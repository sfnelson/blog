package org.sfnelson.blog.shared.content.render.wiki;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 2/11/11
 */
public class List implements Parent, Element {

	private final int indent;
	private final char type;

	public List(int indent, char type) {
		this.indent = indent;
		this.type = type;
	}

	@Override
	public SafeHtml parse(Input input) {
		SafeHtmlBuilder builder = new SafeHtmlBuilder();
		if (type == '#') {
			builder.appendHtmlConstant(input.annotate("ol"));
		} else {
			builder.appendHtmlConstant(input.annotate("ul"));
		}
		while (checkTerminal(input)) {
			int spaces = 0;
			while (input.peek(spaces) == ' ') spaces++;
			if (spaces < indent) {
				break;
			}
			switch (input.peek(spaces)) {
				case '#':
				case '*':
					if (spaces > indent && input.peek(spaces + 1) == ' ') {
						builder.append(new List(spaces, input.peek(spaces)).parse(input));
						continue;
					} else if (input.peek(spaces) == type && input.peek(spaces + 1) == ' ') {
						for (int i = 0; i <= spaces + 1; i++) input.forward(); // nom.
						builder.append(new ListItem(indent).parse(input));
						continue;
					}
			}
			break;
		}
		if (type == '#')
			builder.appendHtmlConstant("</ol>");
		else
			builder.appendHtmlConstant("</ul>");
		eatTerminal(input);
		return builder.toSafeHtml();
	}

	@Override
	public boolean checkTerminal(Input input) {
		switch (input.current()) {
			// list item will already have eaten one newline at this point.
			case '\n':
				return handleNewline(input);
			case 0:
				return false;
			default:
				return true;
		}
	}

	@Override
	public boolean handleNewline(Input input) {
		return false;
	}

	@Override
	public void eatTerminal(Input input) {
		if (input.current() == '\n') input.forward();
	}
}
