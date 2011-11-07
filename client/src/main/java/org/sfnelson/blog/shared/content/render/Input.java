package org.sfnelson.blog.shared.content.render;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public class Input {
	private final String input;
	private final boolean annotate;
	private int position;

	public Input(String input, boolean annotate) {
		this.input = input;
		this.annotate = annotate;
		this.position = 0;
	}

	public String annotate(String tag) {
		if (annotate) return "<" + tag + " s='" + position + "'>";
		else return "<" + tag + ">";
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
