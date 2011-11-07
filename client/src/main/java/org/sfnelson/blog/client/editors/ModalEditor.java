package org.sfnelson.blog.client.editors;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyChange;
import com.google.web.bindery.requestfactory.shared.WriteOperation;

import org.sfnelson.blog.client.events.EditorSelectionEvent;
import org.sfnelson.blog.client.views.ModalEditorView;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public abstract class ModalEditor<T extends EntityProxy, V extends ModalEditorView> extends HasValueEditor<T>
		implements ModalEditorView.Editor<T> {

	private final V view;
	private final EventBus eventBus;
	private final Class<T> type;

	private boolean editing;
	private boolean created;

	protected ModalEditor(V view, EventBus eventBus, Class<T> type) {
		this.view = view;
		this.eventBus = eventBus;
		this.type = type;
	}

	@Ignore
	public V asWidget() {
		return view;
	}

	@Override
	public <X extends T> X create(Class<X> type, Initializer<X> initializer) {
		created = true;
		editing = true;
		X value = doCreate(type, initializer);
		view.edit();
		return value;
	}

	@Ignore
	protected abstract RequestFactoryEditorDriver<T, ?> getDriver();

	@Ignore
	protected abstract <X extends T> X doCreate(Class<X> type, Initializer<X> initializer);

	@Ignore
	protected abstract void doEdit(T value);

	@Override
	public void init(T entry) {
		created = false;
		editing = false;
		getDriver().display(entry);
		view.view();
	}

	public boolean select() {
		view.select();
		if (editing) {
			view.focus();
		}
		return true;
	}

	public boolean deselect() {
		if (editing) {
			if (getDriver().isDirty()) return false;
			requestCancel();
		}
		view.deselect();
		return true;
	}

	@Override
	public void requestSelect() {
		eventBus.fireEvent(new EditorSelectionEvent<T>(getValue(), EditorSelectionEvent.Type.SELECT));
	}

	@Override
	public void requestEdit() {
		if (editing) return;
		editing = true;
		doEdit(getValue());
		view.edit();
	}

	@Override
	public void requestSubmit() {
		if (!editing) return;
		getDriver().flush().fire();
		getDriver().display(getValue());
		reset();
		editing = false;
		view.view();
		if (created) {
			created = false;
		}
	}

	@Override
	public void requestCancel() {
		if (!editing) return;
		reset();
		getDriver().display(getValue());
		editing = false;
		view.view();
		if (created) {
			eventBus.fireEventFromSource(
					new EntityProxyChange<T>(getValue(), WriteOperation.DELETE),
					type);
		}
	}
}
