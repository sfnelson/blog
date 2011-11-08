package org.sfnelson.blog.shared.content.render.wiki;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 2/11/11
 */
public class ListItem extends Inline {

	private final int indent;

	public ListItem(int indent) {
		super("li");
		this.indent = indent;
		setParent(this);
	}

	@Override
	public boolean checkTerminal(Input input) {
		switch (input.current()) {
			case '\n':
				break;
			case 0:
				return false;
			default:
				return true;
		}
		// new line. either this item is continued on the new line or we're done.
		int spaces = 0;
		while (input.peek(1 + spaces) == ' ') spaces++;

		if (spaces < indent) return false; // that's all we can eat.

		switch (input.peek(1 + spaces)) {
			case '#':
			case '*':
				if (input.peek(1 + spaces + 1) == ' ') return false; // new list item
		}

		input.forward(); // eat the newline.
		while (input.peek(1) == ' ') input.forward(); // move the cursor up but leave some whitespace. nom.
		return true;
	}

	@Override
	public void eatTerminal(Input input) {
		if (input.current() == '\n') input.forward();
	}

	@Override
	public boolean handleNewline(Input input) {
		return checkTerminal(input);
	}
}
