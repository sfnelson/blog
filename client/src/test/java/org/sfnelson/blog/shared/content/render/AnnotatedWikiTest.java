package org.sfnelson.blog.shared.content.render;

import org.junit.Test;
import org.sfnelson.blog.domain.Content;

import static org.junit.Assert.assertEquals;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class AnnotatedWikiTest {

	@Test
	public void testRenderPlain() throws Exception {
		Content content = new TestContent("foobar", Content.Type.WIKI);
		assertEquals("<p s='0'>foobar</p>", new ContentRenderer().render(content, true).asString());

		content = new TestContent("foo\nbar\n\nfoobar", Content.Type.WIKI);
		assertEquals("<p s='0'>foo\nbar</p><p s='9'>foobar</p>", new ContentRenderer().render(content, true).asString());
	}

}
