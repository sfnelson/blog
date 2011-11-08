package org.sfnelson.blog.shared.content.render.wiki;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 2/11/11
 */
public class Quote extends Inline {

	public Quote() {
		super("blockquote");
	}

	@Override
	public boolean checkTerminal(Input input) {
		switch (input.current()) {
			case '\n':
				if (input.peek(1) == ' ') {
					input.forward();
					while (input.peek(1) == ' ') input.forward();
					return true;
				} else return false;
			case 0:
				return false;
		}
		return true;
	}

	@Override
	public void eatTerminal(Input input) {
		// nothing to do.
	}
}
