package org.sfnelson.blog.shared.content.render.wiki;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public abstract class Inline implements Element, Parent {

	private final String tag;

	private Parent parent;

	public Inline(String tag) {
		this.tag = tag;
	}

	protected void setParent(Parent parent) {
		this.parent = parent;
	}

	@Override
	public SafeHtml parse(Input input) {
		SafeHtmlBuilder builder = new SafeHtmlBuilder();
		open(builder, input);
		while (checkTerminal(input)) {
			Element e = null;
			switch (input.current()) {
				case '*':
					input.forward();
					e = new Bold(this);
					break;
				case '_':
					input.forward();
					e = new Italic(this);
					break;
				case '`':
					input.forward();
					e = new Code(this);
					break;
				case '^':
					input.forward();
					e = new Superscript(this);
					break;
				case ',':
					if (input.peek(1) == ',') {
						input.forward();
						input.forward();
						e = new Subscript(this);
					}
					break;
				case '~':
					if (input.peek(1) == '~') {
						input.forward();
						input.forward();
						e = new Strikeout(this);
					}
					break;
				case '{':
					if (input.peek(1) == '{' && input.peek(2) == '{') {
						input.forward();
						input.forward();
						input.forward();
						e = new InlineCodeBlock(this);
					}
					break;
			}
			if (e == null) {
				e = new TextNode(this);
			}
			builder.append(e.parse(input));
		}
		eatTerminal(input);
		close(builder, input);
		return builder.toSafeHtml();
	}

	@Override
	public boolean handleNewline(Input input) {
		return parent.handleNewline(input);
	}

	protected void open(SafeHtmlBuilder builder, Input input) {
		builder.append(input.annotateOpen(tag));
	}

	protected void close(SafeHtmlBuilder builder, Input input) {
		builder.append(input.annotateClose(tag));
	}
}
