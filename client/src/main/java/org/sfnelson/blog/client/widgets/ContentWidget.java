package org.sfnelson.blog.client.widgets;

import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.HasEditorDelegate;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.web.bindery.event.shared.HandlerRegistration;

import org.sfnelson.blog.client.request.ContentProxy;
import org.sfnelson.blog.server.domain.Content;
import org.sfnelson.blog.shared.content.render.ContentRenderer;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class ContentWidget extends Composite implements LeafValueEditor<ContentProxy>, HasEditorDelegate<ContentProxy> {

	private final HTML content;
	private final ContentRenderer renderer;

	private HandlerRegistration registration;
	private EditorDelegate<ContentProxy> delegate;

	private ContentProxy value;

	public ContentWidget() {
		renderer = new ContentRenderer();
		content = new HTML();

		initWidget(content);
	}

	@Override
	public ContentProxy getValue() {
		return value;
	}

	@Override
	public void setValue(ContentProxy value) {
		this.value = value;

		render();

		if (registration == null && delegate != null) {
			registration = delegate.subscribe();
		}
	}

	@Override
	@Ignore
	public void setDelegate(EditorDelegate<ContentProxy> delegate) {
		this.delegate = delegate;
	}

	private void render() {
		if (this.value == null) return;

		Content.Type type = this.value.getType();
		String value = this.value.getValue();

		if (this.value.getType() == null) {
			type = Content.Type.WIKI;
		}
		if (this.value.getValue() == null) {
			value = "";
		}

		SafeHtml toRender;
		switch (type) {
			case HTML:
				toRender = renderer.renderHTML(value, true);
				break;
			case WIKI:
				toRender = renderer.renderWiki(value, true);
				break;
			default:
				toRender = renderer.renderText(value, true);
				break;
		}
		content.setHTML(toRender);
	}
}
