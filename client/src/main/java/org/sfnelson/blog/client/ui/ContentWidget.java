package org.sfnelson.blog.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.sfnelson.blog.client.editors.ContentEditor;
import org.sfnelson.blog.client.request.ContentProxy;
import org.sfnelson.blog.server.domain.Content;
import org.sfnelson.blog.shared.content.render.ContentRenderer;

import java.io.IOException;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 3/11/11
 */
public class ContentWidget extends Composite implements ValueAwareEditor<ContentProxy>, ContentEditor {
	interface Binder extends UiBinder<HTMLPanel, ContentWidget> {}

	interface Style extends CssResource {
		String controls();
		String edit();
		String viewer();
		String preview();
		String previewButton();
	}

	public interface HasContentEditor {
		void startEditingContent();
	}

	@UiField
	TextArea value;

	@UiField
	ValuePicker<Content.Type> type;

	@UiField
	@Ignore
	HTML viewer;

	@Ignore
	@UiField Button preview;

	@Ignore
	@UiField Style style;

	private final ContentRenderer renderer;

	private boolean showPreview = false;

	public ContentWidget() {
		renderer = new ContentRenderer();

		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));
	}

	@Override
	public Editor<String> valueEditor() {
		return value.asEditor();
	}

	@Override
	public Editor<org.sfnelson.blog.domain.Content.Type> typeEditor() {
		return type.asEditor();
	}

	private HasContentEditor parent;

	public void setParent(HasContentEditor parent) {
		this.parent = parent;
	}

	public void edit() {
		addStyleName(style.edit());
	}

	public void done() {
		removeStyleName(style.edit());
		showPreview = false;

		render();
	}

	@Override
	public void flush() {
		// nothing to do.
	}

	@Override
	public void onPropertyChange(String... paths) {
		render();
	}

	@Override
	public void setValue(ContentProxy value) {
		System.out.println(String.valueOf(value));
		render();
	}

	@Override
	public void setDelegate(EditorDelegate<ContentProxy> delegate) {
		delegate.subscribe();
	}

	@UiHandler("viewer")
	void viewerClicked(ClickEvent ev) {
		parent.startEditingContent();
	}

	@UiHandler("value")
	void editorBlurred(BlurEvent ev) {
		done();
		removeStyleName(style.preview());
	}

	@UiHandler("preview")
	void togglePreview(ClickEvent ev) {
		addStyleName(style.preview());
		showPreview = true;
		render();
	}

	@UiHandler("value")
	void valueChanged(KeyUpEvent ev) {
		if (showPreview) {
			render();
		}
	}

	private void render() {
		SafeHtml toRender;
		if (type.getValue() == null) {
			type.setValue(Content.Type.WIKI);
		}
		switch (type.getValue()) {
			case HTML:
				toRender = renderer.renderHTML(value.getValue());
				break;
			case WIKI:
				toRender = renderer.renderWiki(value.getValue());
				break;
			default:
				toRender = renderer.renderText(value.getValue());
				break;
		}
		viewer.setHTML(toRender);
	}

	@UiFactory
	ValuePicker<Content.Type> createTypePicker() {
		Renderer<Content.Type> renderer = new Renderer<Content.Type>() {
			@Override
			public String render(Content.Type type) {
				switch (type) {
					case HTML: return "HTML";
					case TEXT: return "Plain text";
					case WIKI: return "Wiki Markup";
				}
				return null;
			}
			@Override
			public void render(Content.Type type, Appendable appendable) throws IOException {
				appendable.append(render(type));
			}
		};
		return new ValuePicker<Content.Type>(renderer);
	}
}