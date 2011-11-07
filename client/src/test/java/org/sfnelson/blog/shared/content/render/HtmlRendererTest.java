package org.sfnelson.blog.shared.content.render;

import org.junit.Test;
import org.sfnelson.blog.domain.Content;

import static org.junit.Assert.assertEquals;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 2/11/11
 */
public class HtmlRendererTest {
	@Test
	public void testRender() throws Exception {
		TestContent content = new TestContent("<p>Test <b>Content</b></p> <iframe>", Content.Type.HTML);
		assertEquals("<p>Test <b>Content</b></p> &lt;iframe&gt;", new ContentRenderer().render(content).asString());
	}
}
