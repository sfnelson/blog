package org.sfnelson.blog.client.ui;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.HandlerRegistration;

import org.sfnelson.blog.client.editors.RootEditor;
import org.sfnelson.blog.client.request.ContentProxy;
import org.sfnelson.blog.server.domain.Content;
import org.sfnelson.blog.shared.content.render.ContentRenderer;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 3/11/11
 */
public class ContentWidget extends Composite implements ValueAwareEditor<ContentProxy> {
	interface Binder extends UiBinder<HTMLPanel, ContentWidget> {
	}

	interface Style extends CssResource {
		String controls();

		String edit();

		String viewer();

		String preview();

		String previewButton();

		String value();
	}

	@UiField
	TextArea value;

	@UiField
	ValuePicker<Content.Type> type;

	@UiField
	@Ignore
	HTML viewer;

	@Ignore
	@UiField
	Button preview;

	@Ignore
	@UiField
	Style style;

	private final ContentRenderer renderer;

	private boolean showPreview = false;

	private HandlerRegistration registration;
	private EditorDelegate<ContentProxy> delegate;

	public ContentWidget() {
		renderer = new ContentRenderer();

		initWidget(GWT.<Binder>create(Binder.class).createAndBindUi(this));
	}

	private RootEditor.HasDelegates parent;

	public void setParent(RootEditor.HasDelegates parent) {
		this.parent = parent;
	}

	public void edit() {
		addStyleName(style.edit());

		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				value.setFocus(true);
			}
		});
	}

	public void done() {
		removeStyleName(style.edit());
		removeStyleName(style.preview());
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
		render();

		if (registration == null && delegate != null) {
			registration = delegate.subscribe();
		}
	}

	@Override
	public void setDelegate(EditorDelegate<ContentProxy> delegate) {
		this.delegate = delegate;
	}

	@UiHandler("viewer")
	void viewerClicked(ClickEvent ev) {
		parent.startEditing(this);
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

	@UiHandler("value")
	void navigation(KeyDownEvent ev) {
		switch (ev.getNativeKeyCode()) {
			case KeyCodes.KEY_ESCAPE:
				parent.doneEditing(this);
				break;
			case KeyCodes.KEY_TAB:
				if (ev.isShiftKeyDown()) {
					parent.editPrevious(this);
				} else {
					parent.editNext(this);
				}
				break;
			default:
				return;
		}
		ev.preventDefault();
		ev.stopPropagation();
	}

	@UiHandler("value")
	void swallowKeyPresses(KeyPressEvent ev) {
		ev.stopPropagation();
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
	@Ignore
	ValuePicker<Content.Type> createTypePicker() {
		Renderer<Content.Type> renderer = new Renderer<Content.Type>() {
			@Override
			public String render(Content.Type type) {
				switch (type) {
					case HTML:
						return "HTML";
					case TEXT:
						return "Plain text";
					case WIKI:
						return "Wiki Markup";
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