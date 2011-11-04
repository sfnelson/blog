package org.sfnelson.blog.shared.content.render;

import org.junit.Test;
import org.sfnelson.blog.domain.Content;
import static org.junit.Assert.assertEquals;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public class WikiRendererTest {
	@Test
	public void testRenderPlain() throws Exception {
		Content content = new TestContent("foobar", Content.Type.WIKI);
		assertEquals("<p>foobar</p>", new ContentRenderer().render(content).asString());

		content = new TestContent("foo\nbar\n\nfoobar", Content.Type.WIKI);
		assertEquals("<p>foo\nbar</p><p>foobar</p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testRenderBold() throws Exception {
		Content content = new TestContent("*foobar*", Content.Type.WIKI);
		assertEquals("<p><strong>foobar</strong></p>", new ContentRenderer().render(content).asString());
		content = new TestContent("*foo*bar**", Content.Type.WIKI);
		assertEquals("<p><strong>foo</strong>bar<strong></strong></p>", new ContentRenderer().render(content).asString());
		content = new TestContent("*null", Content.Type.WIKI);
		assertEquals("<p><strong>null</strong></p>", new ContentRenderer().render(content).asString());
		content = new TestContent("*newline\n\n", Content.Type.WIKI);
		assertEquals("<p><strong>newline</strong></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testRenderItalic() throws Exception {
		Content content = new TestContent("_foobar_ baz", Content.Type.WIKI);
		assertEquals("<p><em>foobar</em> baz</p>", new ContentRenderer().render(content).asString());
		content = new TestContent("_null", Content.Type.WIKI);
		assertEquals("<p><em>null</em></p>", new ContentRenderer().render(content).asString());
		content = new TestContent("_newline\n\n", Content.Type.WIKI);
		assertEquals("<p><em>newline</em></p>", new ContentRenderer().render(content).asString());

	}

	@Test
	public void testRenderCode() throws Exception {
		Content content = new TestContent("`foobar()` baz", Content.Type.WIKI);
		assertEquals("<p><code>foobar()</code> baz</p>", new ContentRenderer().render(content).asString());
		content = new TestContent("`null", Content.Type.WIKI);
		assertEquals("<p><code>null</code></p>", new ContentRenderer().render(content).asString());
		content = new TestContent("`newline\n\n", Content.Type.WIKI);
		assertEquals("<p><code>newline</code></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testRenderCode2() throws Exception {
		Content content = new TestContent("{{{foobar()}}} baz", Content.Type.WIKI);
		assertEquals("<p><code>foobar()</code> baz</p>", new ContentRenderer().render(content).asString());
		content = new TestContent("{{{null", Content.Type.WIKI);
		assertEquals("<p><code>null</code></p>", new ContentRenderer().render(content).asString());
		content = new TestContent("{{{newline\n\n", Content.Type.WIKI);
		assertEquals("<p><code>newline</code></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testSuperscript() throws Exception {
		Content content = new TestContent("^super^script", Content.Type.WIKI);
		assertEquals("<p><sup>super</sup>script</p>", new ContentRenderer().render(content).asString());
		content = new TestContent("^super", Content.Type.WIKI);
		assertEquals("<p><sup>super</sup></p>", new ContentRenderer().render(content).asString());
		content = new TestContent("^super\n\n", Content.Type.WIKI);
		assertEquals("<p><sup>super</sup></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testSubscript() throws Exception {
		Content content = new TestContent(",,sub,,script", Content.Type.WIKI);
		assertEquals("<p><sub>sub</sub>script</p>", new ContentRenderer().render(content).asString());
		content = new TestContent(",,sub", Content.Type.WIKI);
		assertEquals("<p><sub>sub</sub></p>", new ContentRenderer().render(content).asString());
		content = new TestContent(",,sub\n\n", Content.Type.WIKI);
		assertEquals("<p><sub>sub</sub></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testStrikeout() throws Exception {
		Content content = new TestContent("~~strikeout~~", Content.Type.WIKI);
		assertEquals("<p><span style='text-decoration: line-through;'>strikeout</span></p>",
				new ContentRenderer().render(content).asString());
		content = new TestContent("~~foo", Content.Type.WIKI);
		assertEquals("<p><span style='text-decoration: line-through;'>foo</span></p>",
				new ContentRenderer().render(content).asString());
		content = new TestContent("~~foo\n\n", Content.Type.WIKI);
		assertEquals("<p><span style='text-decoration: line-through;'>foo</span></p>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testBoldInItalics() throws Exception {
		Content content = new TestContent("_*bold* in italics_", Content.Type.WIKI);
		assertEquals("<p><em><strong>bold</strong> in italics</em></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testItalicsInBold() throws Exception {
		Content content = new TestContent("*_italics_ in bold*", Content.Type.WIKI);
		assertEquals("<p><strong><em>italics</em> in bold</strong></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testStrike() throws Exception {
		Content content = new TestContent("*~~strike~~ works too*", Content.Type.WIKI);
		assertEquals("<p><strong><span style='text-decoration: line-through;'>strike</span> works too</strong></p>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testAsWellAs() throws Exception {
		Content content = new TestContent("~~as well as _this_ way round~~", Content.Type.WIKI);
		assertEquals("<p><span style='text-decoration: line-through;'>as well as <em>this</em> way round</span></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testCodeBlock() throws Exception {
		Content content = new TestContent("{{{\n" +
				"def fib(n):\n" +
				"  if n == 0 or n == 1:\n" +
				"    return n\n" +
				"  else:\n" +
				"    # This recursion is not good for large numbers.\n" +
				"    return fib(n-1) + fib(n-2)\n" +
				"}}}", Content.Type.WIKI);
		assertEquals("<code>" +
				"def fib(n):\n" +
				"  if n == 0 or n == 1:\n" +
				"    return n\n" +
				"  else:\n" +
				"    # This recursion is not good for large numbers.\n" +
				"    return fib(n-1) + fib(n-2)" +
				"</code>", new ContentRenderer().render(content).asString());

		content = new TestContent("foo\n{{{\nbar\n}}}", Content.Type.WIKI);
		assertEquals("<p>foo</p><code>bar</code>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testHeadings() throws Exception {
		Content content = new TestContent("= Heading =\n" +
				"== Subheading ==\n" +
				"=== Level 3 ===\n" +
				"==== Level 4 ====\n" +
				"===== Level 5 =====\n" +
				"====== Level 6 ======\n", Content.Type.WIKI);
		assertEquals("<h1> Heading </h1>\n" +
				"<h2> Subheading </h2>\n" +
				"<h3> Level 3 </h3>\n" +
				"<h4> Level 4 </h4>\n" +
				"<h5> Level 5 </h5>\n" +
				"<h6> Level 6 </h6>\n", new ContentRenderer().render(content).asString());

		content = new TestContent("=Heading?", Content.Type.WIKI);
		assertEquals("<h1>Heading?</h1>\n", new ContentRenderer().render(content).asString());
		content = new TestContent("=Heading!\n\n", Content.Type.WIKI);
		assertEquals("<h1>Heading!</h1>\n", new ContentRenderer().render(content).asString());

		content = new TestContent("foo\n=bar", Content.Type.WIKI);
		assertEquals("<p>foo</p><h1>bar</h1>\n", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testDivider() throws Exception {
		Content content = new TestContent("----\n" +
				"-----\n" +
				"---- no rule\n" +
				"----", Content.Type.WIKI);
		assertEquals("<hr />\n" +
				"<hr />\n" +
				"<p>---- no rule</p><hr />\n",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testLists() throws Exception {
		Content content = new TestContent("The following is:\n" +
				"  * A list\n" +
				"  * Of bulleted items\n" +
				"    # This is a numbered sublist\n" +
				"    # Which is done by indenting further\n" +
				"  * And back to the main bulleted list\n" +
				"\n" +
				" * This is also a list\n" +
				" * With a single leading space\n" +
				" * Notice that it is rendered\n" +
				"  # At the same levels\n" +
				"  # As the above lists.\n" +
				" * Despite the different indentation levels.", Content.Type.WIKI);
		assertEquals("<p>The following is:</p><ul><li>A list</li>" +
				"<li>Of bulleted items</li>" +
				"<ol><li>This is a numbered sublist</li>" +
				"<li>Which is done by indenting further</li></ol>" +
				"<li>And back to the main bulleted list</li></ul>" +
				"<ul><li>This is also a list</li><li>With a single leading space</li>" +
				"<li>Notice that it is rendered</li>" +
				"<ol><li>At the same levels</li><li>As the above lists.</li></ol>" +
				"<li>Despite the different indentation levels.</li></ul>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testList2() throws Exception {
		Content content = new TestContent(
				"  * A long list\n" +
				"  item. It just\n" +
				"    goes on and on!", Content.Type.WIKI);
		assertEquals("<ul><li>A long list item. It just goes on and on!</li></ul>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testList3() throws Exception {
		Content content = new TestContent(
				"  * List A\n" +
				" * List B\n", Content.Type.WIKI);
		assertEquals("<ul><li>List A</li></ul><ul><li>List B</li></ul>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testList4() throws Exception {
		Content content = new TestContent(
				"  * *Item A\n  is bold*\n" +
				"  * _Item B\n  is italic", Content.Type.WIKI);
		assertEquals("<ul><li><strong>Item A is bold</strong></li><li><em>Item B is italic</em></li></ul>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testList5() throws Exception {
		Content content = new TestContent(
				"  # List A\n" +
				"  * List B\n" +
				"Not a list", Content.Type.WIKI);
		assertEquals("<ol><li>List A</li></ol><ul><li>List B</li></ul><p>Not a list</p>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testEmbedded() throws Exception {
		Content content = new TestContent("Foo *bar* baz", Content.Type.WIKI);
		assertEquals("<p>Foo <strong>bar</strong> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new TestContent("Foo _bar_ baz", Content.Type.WIKI);
		assertEquals("<p>Foo <em>bar</em> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new TestContent("Foo `bar` baz", Content.Type.WIKI);
		assertEquals("<p>Foo <code>bar</code> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new TestContent("Foo {{{bar}}} baz", Content.Type.WIKI);
		assertEquals("<p>Foo <code>bar</code> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new TestContent("Foo ~~bar~~ baz", Content.Type.WIKI);
		assertEquals("<p>Foo <span style='text-decoration: line-through;'>bar</span> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new TestContent("Foo ^bar^ baz", Content.Type.WIKI);
		assertEquals("<p>Foo <sup>bar</sup> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new TestContent("Foo ,,bar,, baz", Content.Type.WIKI);
		assertEquals("<p>Foo <sub>bar</sub> baz</p>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testQuote() throws Exception {
		Content content = new TestContent("Someone once said:\n" +
				"\n" +
				"  This sentence will be quoted in the future as the canonical example\n" +
				"  of a quote that is so important that it should be visually separate\n" +
				"  from the rest of the text in which it appears.", Content.Type.WIKI);
		assertEquals("<p>Someone once said:</p>" +
				"<blockquote>This sentence will be quoted in the future as the canonical example " +
				"of a quote that is so important that it should be visually separate " +
				"from the rest of the text in which it appears.</blockquote>",
				new ContentRenderer().render(content).asString());

		content = new TestContent(" Quote\nReply", Content.Type.WIKI);
		assertEquals("<blockquote>Quote</blockquote><p>Reply</p>",
				new ContentRenderer().render(content).asString());
	}

}
