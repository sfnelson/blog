package org.sfnelson.blog.shared.content.render;

import org.junit.Test;
import org.sfnelson.blog.domain.Content;

import static org.junit.Assert.assertEquals;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 2/11/11
 */
public class TextRendererTest {
	@Test
	public void testRender() throws Exception {
		TestContent content = new TestContent("Test <b>Content</b>\n<iframe>", Content.Type.TEXT);
		assertEquals("Test &lt;b&gt;Content&lt;/b&gt;<br>&lt;iframe&gt;",
				new ContentRenderer().render(content).asString());
	}
}
