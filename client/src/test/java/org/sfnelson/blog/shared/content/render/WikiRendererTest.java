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
		Content content = new ContentImplForTesting("foobar", Content.Type.WIKI);
		assertEquals("<p>foobar</p>", new ContentRenderer().render(content).asString());

		content = new ContentImplForTesting("foo\nbar\n\nfoobar", Content.Type.WIKI);
		assertEquals("<p>foo\nbar</p><p>foobar</p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testRenderBold() throws Exception {
		Content content = new ContentImplForTesting("*foobar*", Content.Type.WIKI);
		assertEquals("<p><strong>foobar</strong></p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("*foo*bar**", Content.Type.WIKI);
		assertEquals("<p><strong>foo</strong>bar<strong></strong></p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("*null", Content.Type.WIKI);
		assertEquals("<p><strong>null</strong></p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("*newline\n\n", Content.Type.WIKI);
		assertEquals("<p><strong>newline</strong></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testRenderItalic() throws Exception {
		Content content = new ContentImplForTesting("_foobar_ baz", Content.Type.WIKI);
		assertEquals("<p><em>foobar</em> baz</p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("_null", Content.Type.WIKI);
		assertEquals("<p><em>null</em></p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("_newline\n\n", Content.Type.WIKI);
		assertEquals("<p><em>newline</em></p>", new ContentRenderer().render(content).asString());

	}

	@Test
	public void testRenderCode() throws Exception {
		Content content = new ContentImplForTesting("`foobar()` baz", Content.Type.WIKI);
		assertEquals("<p><code>foobar()</code> baz</p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("`null", Content.Type.WIKI);
		assertEquals("<p><code>null</code></p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("`newline\n\n", Content.Type.WIKI);
		assertEquals("<p><code>newline</code></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testRenderCode2() throws Exception {
		Content content = new ContentImplForTesting("{{{foobar()}}} baz", Content.Type.WIKI);
		assertEquals("<p><code>foobar()</code> baz</p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("{{{null", Content.Type.WIKI);
		assertEquals("<p><code>null</code></p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("{{{newline\n\n", Content.Type.WIKI);
		assertEquals("<p><code>newline</code></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testSuperscript() throws Exception {
		Content content = new ContentImplForTesting("^super^script", Content.Type.WIKI);
		assertEquals("<p><sup>super</sup>script</p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("^super", Content.Type.WIKI);
		assertEquals("<p><sup>super</sup></p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("^super\n\n", Content.Type.WIKI);
		assertEquals("<p><sup>super</sup></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testSubscript() throws Exception {
		Content content = new ContentImplForTesting(",,sub,,script", Content.Type.WIKI);
		assertEquals("<p><sub>sub</sub>script</p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting(",,sub", Content.Type.WIKI);
		assertEquals("<p><sub>sub</sub></p>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting(",,sub\n\n", Content.Type.WIKI);
		assertEquals("<p><sub>sub</sub></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testStrikeout() throws Exception {
		Content content = new ContentImplForTesting("~~strikeout~~", Content.Type.WIKI);
		assertEquals("<p><strike>strikeout</strike></p>",
				new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("~~foo", Content.Type.WIKI);
		assertEquals("<p><strike>foo</strike></p>",
				new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("~~foo\n\n", Content.Type.WIKI);
		assertEquals("<p><strike>foo</strike></p>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testBoldInItalics() throws Exception {
		Content content = new ContentImplForTesting("_*bold* in italics_", Content.Type.WIKI);
		assertEquals("<p><em><strong>bold</strong> in italics</em></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testItalicsInBold() throws Exception {
		Content content = new ContentImplForTesting("*_italics_ in bold*", Content.Type.WIKI);
		assertEquals("<p><strong><em>italics</em> in bold</strong></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testStrike() throws Exception {
		Content content = new ContentImplForTesting("*~~strike~~ works too*", Content.Type.WIKI);
		assertEquals("<p><strong><strike>strike</strike> works too</strong></p>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testAsWellAs() throws Exception {
		Content content = new ContentImplForTesting("~~as well as _this_ way round~~", Content.Type.WIKI);
		assertEquals("<p><strike>as well as <em>this</em> way round</strike></p>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testCodeBlock() throws Exception {
		Content content = new ContentImplForTesting("{{{\n" +
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

		content = new ContentImplForTesting("foo\n{{{\nbar\n}}}", Content.Type.WIKI);
		assertEquals("<p>foo</p><code>bar</code>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testHeadings() throws Exception {
		Content content = new ContentImplForTesting("= Heading =\n" +
				"== Subheading ==\n" +
				"=== Level 3 ===\n" +
				"==== Level 4 ====\n" +
				"===== Level 5 =====\n" +
				"====== Level 6 ======\n", Content.Type.WIKI);
		assertEquals("<h1> Heading </h1>" +
				"<h2> Subheading </h2>" +
				"<h3> Level 3 </h3>" +
				"<h4> Level 4 </h4>" +
				"<h5> Level 5 </h5>" +
				"<h6> Level 6 </h6>", new ContentRenderer().render(content).asString());

		content = new ContentImplForTesting("=Heading?", Content.Type.WIKI);
		assertEquals("<h1>Heading?</h1>", new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("=Heading!\n\n", Content.Type.WIKI);
		assertEquals("<h1>Heading!</h1>", new ContentRenderer().render(content).asString());

		content = new ContentImplForTesting("foo\n=bar", Content.Type.WIKI);
		assertEquals("<p>foo</p><h1>bar</h1>", new ContentRenderer().render(content).asString());
	}

	@Test
	public void testDivider() throws Exception {
		Content content = new ContentImplForTesting("----\n" +
				"-----\n" +
				"---- no rule\n" +
				"----", Content.Type.WIKI);
		assertEquals("<hr /><hr /><p>---- no rule</p><hr />",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testLists() throws Exception {
		Content content = new ContentImplForTesting("The following is:\n" +
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
		Content content = new ContentImplForTesting(
				"  * A long list\n" +
						"  item. It just\n" +
						"    goes on and on!", Content.Type.WIKI);
		assertEquals("<ul><li>A long list item. It just goes on and on!</li></ul>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testList3() throws Exception {
		Content content = new ContentImplForTesting(
				"  * List A\n" +
						" * List B\n", Content.Type.WIKI);
		assertEquals("<ul><li>List A</li></ul><ul><li>List B</li></ul>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testList4() throws Exception {
		Content content = new ContentImplForTesting(
				"  * *Item A\n  is bold*\n" +
						"  * _Item B\n  is italic", Content.Type.WIKI);
		assertEquals("<ul><li><strong>Item A is bold</strong></li><li><em>Item B is italic</em></li></ul>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testList5() throws Exception {
		Content content = new ContentImplForTesting(
				"  # List A\n" +
						"  * List B\n" +
						"Not a list", Content.Type.WIKI);
		assertEquals("<ol><li>List A</li></ol><ul><li>List B</li></ul><p>Not a list</p>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testEmbedded() throws Exception {
		Content content = new ContentImplForTesting("Foo *bar* baz", Content.Type.WIKI);
		assertEquals("<p>Foo <strong>bar</strong> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("Foo _bar_ baz", Content.Type.WIKI);
		assertEquals("<p>Foo <em>bar</em> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("Foo `bar` baz", Content.Type.WIKI);
		assertEquals("<p>Foo <code>bar</code> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("Foo {{{bar}}} baz", Content.Type.WIKI);
		assertEquals("<p>Foo <code>bar</code> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("Foo ~~bar~~ baz", Content.Type.WIKI);
		assertEquals("<p>Foo <strike>bar</strike> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("Foo ^bar^ baz", Content.Type.WIKI);
		assertEquals("<p>Foo <sup>bar</sup> baz</p>",
				new ContentRenderer().render(content).asString());
		content = new ContentImplForTesting("Foo ,,bar,, baz", Content.Type.WIKI);
		assertEquals("<p>Foo <sub>bar</sub> baz</p>",
				new ContentRenderer().render(content).asString());
	}

	@Test
	public void testQuote() throws Exception {
		Content content = new ContentImplForTesting("Someone once said:\n" +
				"\n" +
				"  This sentence will be quoted in the future as the canonical example\n" +
				"  of a quote that is so important that it should be visually separate\n" +
				"  from the rest of the text in which it appears.", Content.Type.WIKI);
		assertEquals("<p>Someone once said:</p>" +
				"<blockquote>This sentence will be quoted in the future as the canonical example " +
				"of a quote that is so important that it should be visually separate " +
				"from the rest of the text in which it appears.</blockquote>",
				new ContentRenderer().render(content).asString());

		content = new ContentImplForTesting(" Quote\nReply", Content.Type.WIKI);
		assertEquals("<blockquote>Quote</blockquote><p>Reply</p>",
				new ContentRenderer().render(content).asString());
	}

}
