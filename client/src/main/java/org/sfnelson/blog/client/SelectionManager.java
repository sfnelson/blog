package org.sfnelson.blog.client;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

import org.sfnelson.blog.client.editors.SelectableEditor;
import org.sfnelson.blog.client.events.EditorSelectionEvent;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 6/11/11
 */
public class SelectionManager {

	private final EditorMapper mapper;

	private EntityProxyId<?> currentId;
	private SelectableEditor currentSelection;

	public SelectionManager(EditorMapper mapper) {
		this.mapper = mapper;
	}

	public void register(EventBus eventBus) {
		EditorSelectionEvent.register(new EditorSelectionEvent.Handler() {
			@Override
			public void onSelectionEvent(EditorSelectionEvent event) {
				switch (event.getType()) {
					case SELECT:
						select(event.getEntryId(), true);
						break;
					case BLUR:
						select(currentId, false);
						break;
					case PREVIOUS:
						if (currentId != null) {
							EntityProxyId<?> prev = mapper.getPrevious(currentId);
							if (prev != null) {
								select(prev, true);
							}
						}
						break;
					case NEXT:
						if (currentId != null) {
							EntityProxyId<?> next = mapper.getNext(currentId);
							if (next != null) {
								select(next, true);
							}
						}
						break;
				}
			}
		}, eventBus);
	}

	void select(EntityProxyId<?> newId, boolean select) {

		if (newId != null && newId.equals(currentId)) {
			// already selected

			if (select) {
				return;
			} else if (currentSelection.deselect()) {
				currentId = null;
				currentSelection = null;
			}
		}

		if (!select) return; // not selected, so nothing to do.

		SelectableEditor newSelection = mapper.getEditor(newId);

		if (newSelection != null && newSelection.equals(currentSelection)) {
			return; // already selected.
		}

		boolean ready = (currentSelection == null || currentSelection.deselect());

		if (ready) {
			currentId = null;
			currentSelection = null;
			if (newSelection != null && newSelection.select()) {
				currentId = newId;
				currentSelection = newSelection;
			}
		}
	}
}
