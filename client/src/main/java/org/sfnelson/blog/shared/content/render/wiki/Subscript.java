package org.sfnelson.blog.shared.content.render.wiki;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public class Subscript extends Inline {

	public Subscript(Parent parent) {
		super("sub");
		setParent(parent);
	}

	@Override
	public boolean checkTerminal(Input input) {
		switch (input.current()) {
			case ',':
				return input.peek(1) != ',';
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
		if (input.current() == ',' && input.peek(1) == ',') {
			input.forward();
			input.forward();
		}
	}
}
