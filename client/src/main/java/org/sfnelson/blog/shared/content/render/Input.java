package org.sfnelson.blog.shared.content.render;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

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

	public SafeHtml annotateOpen(String tag) {
		String value;
		if (annotate) value = "<" + tag + " s='" + position + "'>";
		else value = "<" + tag + ">";
		return SafeHtmlUtils.fromTrustedString(value);
	}

	public SafeHtml annotateClose(String tag) {
		String value = "</" + tag + ">";
		return SafeHtmlUtils.fromTrustedString(value);
	}

	public SafeHtml annotateLeaf(String tag) {
		String value;
		if (annotate) value = "<" + tag + " s='" + position + "' />";
		else value = "<" + tag + " />";
		return SafeHtmlUtils.fromTrustedString(value);
	}

	public boolean done() {
		if (input == null) return true;
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
