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
		TestContent content = new TestContent("Test <b>Content</b> <iframe>", Content.Type.HTML);
		assertEquals("Test <b>Content</b> &lt;iframe&gt;", new ContentRenderer().render(content).asString());
	}
}
