package org.sfnelson.blog.shared.content.render.wiki;

import org.sfnelson.blog.shared.content.render.Input;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public interface Parent {
	boolean handleNewline(Input input);

	boolean checkTerminal(Input input);

	void eatTerminal(Input input);
}
