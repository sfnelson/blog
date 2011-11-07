package org.sfnelson.blog.shared.content.render.wiki;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public class InlineCodeBlock extends Inline {

	public InlineCodeBlock(Parent parent) {
		setParent(parent);
	}

	@Override
	public boolean checkTerminal(Input input) {
		switch (input.current()) {
			case '}':
				return input.peek(1) != '}' || input.peek(2) != '}';
			case '\n':
				return handleNewline(input);
			case 0:
				return false;
			default:
				return true;
		}
	}

	@Override
	public void eatTerminal(Input input) {
		if (input.current() == '}'
				&& input.peek(1) == '}'
				&& input.peek(2) == '}') {
			input.forward();
			input.forward();
			input.forward();
		}
	}

	@Override
	protected void open(SafeHtmlBuilder builder, Input input) {
		builder.appendHtmlConstant(input.annotate("code"));
	}

	@Override
	protected void close(SafeHtmlBuilder builder, Input input) {
		builder.appendHtmlConstant("</code>");
	}
}
