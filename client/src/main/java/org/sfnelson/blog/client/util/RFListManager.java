package org.sfnelson.blog.client.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gwt.editor.client.Editor;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyChange;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 28/10/11
 */
public abstract class RFListManager<T extends EntityProxy, E extends Editor<T>> implements EntityProxyChange.Handler<T> {

	private final List<EntityProxyId<T>> created = Lists.newArrayList();
	private final List<EntityProxyId<T>> current = Lists.newArrayList();

	public RFListManager(Class<T> type, EventBus eventBus) {
		EntityProxyChange.registerForProxyType(eventBus, type, this);
	}

	@SuppressWarnings("unchecked")
	public void addCreated(T entity) {
		created.add(0, (EntityProxyId<T>) entity.stableId());
	}

	@Override
	public void onProxyChange(EntityProxyChange<T> event) {
		switch (event.getWriteOperation()) {
			case PERSIST:
				created.remove(event.getProxyId());
				current.add(0, event.getProxyId());
				break;
			case DELETE:
				remove(event.getProxyId());
				created.remove(event.getProxyId());
				current.remove(event.getProxyId());
				break;
		}
	}

	@SuppressWarnings("unchecked")
	public void update(List<T> update) {
		Set<EntityProxyId<T>> toRemove = Sets.newHashSet();
		toRemove.addAll(current);
		for (T entity: update) {
			toRemove.remove(entity.stableId());
		}
		for (EntityProxyId<T> id: toRemove) {
			remove(id);
			current.remove(id);
		}

		for (int i = 0; i < update.size(); i++) {
			T entity = update.get(i);
			EntityProxyId<T> id = (EntityProxyId<T>) entity.stableId();
			if (current.size() > i && current.get(i).equals(id)) {
				continue;
			}
			else {
				add(created.size() + i, entity);
				current.add(i, id);
			}
		}
	}

	protected abstract void add(int position, T entity);
	protected abstract void remove(EntityProxyId<T> id);
}
