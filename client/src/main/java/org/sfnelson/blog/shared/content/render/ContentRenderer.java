package org.sfnelson.blog.shared.content.render;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.sfnelson.blog.domain.Content;
import org.sfnelson.blog.shared.content.render.html.HtmlSanitizer;
import org.sfnelson.blog.shared.content.render.wiki.Document;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 1/11/11
 */
public class ContentRenderer {

	public SafeHtml render(Content content) {
		return render(content, false);
	}

	public SafeHtml render(Content content, boolean annotated) {
		if (content == null || content.getType() == null) {
			if (content == null || content.getValue() == null) {
				return new SafeHtmlBuilder().toSafeHtml();
			} else {
				return new SafeHtmlBuilder()
						.appendEscaped(content.getValue())
						.toSafeHtml();
			}
		}
		switch (content.getType()) {
			case TEXT:
				return renderText(content.getValue(), annotated);
			case HTML:
				return renderHTML(content.getValue(), annotated);
			case WIKI:
				return renderWiki(content.getValue(), annotated);
		}
		return null;
	}

	public SafeHtml renderText(String value, boolean annotated) {
		return new SafeHtmlBuilder()
				.appendEscapedLines(value).toSafeHtml();
	}

	public SafeHtml renderHTML(String value, boolean annotated) {
		return HtmlSanitizer.sanitizeHtml(value);
	}

	public SafeHtml renderWiki(String value, boolean annotated) {
		Input input = new Input(value, annotated);
		return new Document().parse(input);
	}
}
