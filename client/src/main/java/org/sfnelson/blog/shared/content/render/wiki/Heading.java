package org.sfnelson.blog.shared.content.render.wiki;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 2/11/11
 */
public class Heading extends Inline {

	private final int depth;

	public Heading(int depth) {
		super("h" + depth);
		this.depth = depth;
		setParent(this);
	}

	@Override
	public boolean checkTerminal(Input input) {
		switch (input.current()) {
			case '=':
				for (int i = 1; i < depth; i++) {
					if (input.peek(i) != '=') return true;
				}
				if (input.peek(depth) != '\n') return true;
				else return false;
			case '\n':
				return false;
			case 0:
				return false;
			default:
				return true;
		}
	}

	@Override
	public void eatTerminal(Input input) {
		for (int i = 0; i < depth; i++) {
			if (input.peek(i) != '=') return;
		}
		if (input.peek(depth) != '\n') return;
		for (int i = 0; i < depth; i++) {
			input.forward();
		}
		input.forward();
	}
}
