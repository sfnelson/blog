package org.sfnelson.blog.shared.content.render.wiki;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * A paragraph is any number of lines of text, consisting of inline elements and plain text.
 * Each new line may begin a new block-level element which would terminate this paragraph.
 */
public class Paragraph extends Inline implements Parent {

	public Paragraph() {
		super("p");
		setParent(this);
	}

	@Override
	public boolean checkTerminal(Input input) {
		switch (input.current()) {
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
		assert input.current() == '\n';
		switch (input.peek(1)) {
			case '\n':
				return false; // two newlines is too many. time to gtfo.
			case '=':
				return false; // title starting
			case ' ':
				return false; // a block quote/list is starting.
			case '-':
				if (input.peek(2) == '-' && input.peek(3) == '-' && input.peek(4) == '-')
					return false; // that's a divider folks.
				break;
			case '{':
				if (input.peek(2) == '{' && input.peek(3) == '{' && input.peek(4) == '\n')
					return false; // code block
				break;
		}
		// if we got here we're still good. don't eat anything because whitespace is important.
		return true;
	}

	@Override
	public void eatTerminal(Input input) {
		if (input.current() == '\n') input.forward();
		if (input.current() == '\n') input.forward();
	}
}
