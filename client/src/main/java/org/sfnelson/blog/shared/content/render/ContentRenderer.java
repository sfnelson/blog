package org.sfnelson.blog.shared.content.render;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import org.sfnelson.blog.domain.Content;
import org.sfnelson.blog.shared.content.render.wiki.Document;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public class ContentRenderer {

	public SafeHtml render(Content content) {
		if (content == null || content.getType() == null) {
			if (content == null || content.getValue() == null) {
				return new SafeHtmlBuilder().toSafeHtml();
			}
			else {
				return new SafeHtmlBuilder()
						.appendEscaped(content.getValue())
						.toSafeHtml();
			}
		}
		switch (content.getType()) {
			case TEXT: return renderText(content.getValue());
			case HTML: return renderHTML(content.getValue());
			case WIKI: return renderWiki(content.getValue());
		}
		return null;
	}

	public SafeHtml renderText(String value) {
		return new SafeHtmlBuilder()
				.appendEscapedLines(value).toSafeHtml();
	}

	public SafeHtml renderHTML(String value) {
		return SimpleHtmlSanitizer.sanitizeHtml(value);
	}

	public SafeHtml renderWiki(String value) {
		Input input = new Input(value);
		return new Document().parse(input);
	}
}
