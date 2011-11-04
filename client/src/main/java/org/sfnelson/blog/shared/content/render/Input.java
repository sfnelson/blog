package org.sfnelson.blog.shared.content.render;

/**
* Author: Stephen Nelson <stephen@sfnelson.org>
* Date: 1/11/11
*/
public class Input {
	private final String input;
	private int position;

	public Input(String input) {
		this.input = input;
		this.position = 0;
	}

	public boolean done() {
		return position >= input.length();
	}

	public char current() {
		if (done()) return 0;
		return input.charAt(position);
	}

	public char peek(int distance) {
		if (position + distance >= input.length()) return 0;
		return input.charAt(position + distance);
	}

	public void forward() {
		++position;
	}
}
