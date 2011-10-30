package org.sfnelson.blog.client.editors;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyChange;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.WriteOperation;
import org.sfnelson.blog.client.events.EditorSelectionEvent;
import org.sfnelson.blog.client.views.ModalEditorView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public abstract class ModalEditor<T extends EntityProxy, V extends ModalEditorView<T>>
		extends HasValueEditor<T, V> implements ModalEditorView.Editor<T> {

	private final EventBus eventBus;
	private final Class<T> type;

	private RequestContext edit;
	private boolean created;

	protected ModalEditor(V view, EventBus eventBus, Class<T> type) {
		super(view);
		this.eventBus = eventBus;
		this.type = type;
	}

	protected abstract RequestFactoryEditorDriver<T, ? extends ModalEditor<T, ?>> getDriver();

	protected T create(T value, RequestContext context) {
		created = true;
		edit = context;
		setValue(value);
		getDriver().edit(value, context);
		getView().edit();
		return value;
	}

	@Override
	public void init(T entry) {
		created = false;
		edit = null;
		setValue(entry);
		getDriver().display(entry);
		getView().view();
	}

	public boolean select() {
		getView().select();
		return true;
	}

	public boolean deselect() {
		if (edit != null) {
			if (getDriver().isDirty()) return false;
			requestCancel();
		}
		getView().deselect();
		return true;
	}

	@Override
	public void requestSelect() {
		eventBus.fireEvent(new EditorSelectionEvent(getValue(), true));
	}

	@Override
	public void requestEdit() {
		if (edit != null) return;
		edit = edit();
		getDriver().edit(getValue(), edit);
		getView().edit();
	}

	protected abstract RequestContext edit();

	@Override
	public void requestSubmit() {
		if (edit == null) return;
		getDriver().flush().fire();
		edit = null;
		getView().view();
		if (created) {
			created = false;
		}
	}

	@Override
	public void requestCancel() {
		if (edit == null) return;
		reset();
		getDriver().display(getValue());
		edit = null;
		getView().view();
		if (created) {
			eventBus.fireEventFromSource(
					new EntityProxyChange<T>(getValue(), WriteOperation.DELETE),
					type);
		}
	}
}
