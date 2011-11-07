package org.sfnelson.blog.shared.content.render.wiki;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * A document is any number of blocks terminated by the end of the input.
 */
public class Document implements Element {

	@Override
	public SafeHtml parse(Input input) {
		SafeHtmlBuilder builder = new SafeHtmlBuilder();
		while (!input.done()) {
			Element block = null;
			switch (input.current()) {
				case '\n':
					input.forward();
					continue; // we can munch newlines without indigestion.
				case '=':
					for (int i = 0; i <= 6; i++) {
						if (input.current() == '=') {
							input.forward();
						} else {
							block = new Heading(i);
							break;
						}
					}
					break;
				case '-':
					for (int i = 0; true; i++) {
						switch (input.peek(i)) {
							case '-':
								continue;
							case '\n':
							case 0:
								if (i >= 4) {
									while (input.current() == '-') input.forward();
									block = new Divider();
								}
								break;
							default:
								break;
						}
						break;
					}
					break;
				case '{':
					if (input.peek(1) == '{' && input.peek(2) == '{' && input.peek(3) == '\n') {
						input.forward();
						input.forward();
						input.forward();
						input.forward();
						block = new CodeBlock();
					}
					break;
				case ' ':
					int indent = 0;
					while (input.peek(indent) == ' ') indent++;
					switch (input.peek(indent)) {
						case '#':
						case '*':
							if (input.peek(indent + 1) == ' ') {
								block = new List(indent, input.peek(indent));
								break;
							}
						default:
							while (input.current() == ' ') input.forward();
							block = new Quote();
					}
					break;
			}
			if (block == null) {
				block = new Paragraph();
			}
			builder.append(block.parse(input));
		}
		return builder.toSafeHtml();
	}
}
