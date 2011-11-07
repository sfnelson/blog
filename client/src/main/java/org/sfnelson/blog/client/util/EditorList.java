package org.sfnelson.blog.client.util;

import java.util.AbstractList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.sfnelson.blog.client.EditorMapper;
import org.sfnelson.blog.client.editors.EditorSource;
import org.sfnelson.blog.client.editors.RootEditor;
import org.sfnelson.blog.client.editors.SelectableEditor;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 5/11/11
 */
public class EditorList<T extends EntityProxy> extends AbstractList<T> implements EditorMapper {


	private final List<T> backingList = Lists.newArrayList();
	private final Map<EntityProxyId<?>, RootEditor<? extends T>> editors = Maps.newHashMap();
	private final EditorSource<T> view;

	public EditorList(EditorSource<T> view) {
		this.view = view;
	}

	public <X extends T> X create(int index, Class<X> type) {
		return create(index, type, null);
	}

	public <X extends T> X create(int index, Class<X> type, RootEditor.Initializer<X> initializer) {
		RootEditor<X> editor = view.create(index, type);
		X element = editor.create(type, initializer);
		backingList.add(index, element);
		editors.put(element.stableId(), editor);
		return element;
	}

	@Override
	public SelectableEditor getEditor(EntityProxyId<?> entity) {
		return editors.get(entity);
	}

	@Override
	public EntityProxyId<?> getPrevious(EntityProxyId<?> current) {
		for (ListIterator<T> it = backingList.listIterator(); it.hasNext(); ) {
			T element = it.next();
			if (element.stableId().equals(current)) {
				it.previous(); // move cursor back to where it was
				if (it.hasPrevious()) return it.previous().stableId();
				else return null;
			}
		}
		return null;
	}

	@Override
	public EntityProxyId<?> getNext(EntityProxyId<?> current) {
		for (ListIterator<T> it = backingList.listIterator(); it.hasNext(); ) {
			T element = it.next();
			if (element.stableId().equals(current)) {
				if (it.hasNext()) return it.next().stableId();
				else return null;
			}
		}
		return null;
	}

	@Override
	public T get(int index) {
		return backingList.get(index);
	}

	@Override
	public int size() {
		return backingList.size();
	}

	@Override
	public T set(int index, T element) {
		T removed = remove(index);
		add(index, element);
		return removed;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void add(int index, T element) {
		RootEditor<T> editor = view.create(index, (Class<T>) element.getClass());
		editor.init(element);
		backingList.add(index, element);
		editors.put(element.stableId(), editor);
	}

	@Override
	public T remove(int index) {
		T removed = backingList.remove(index);
		if (removed != null) {
			RootEditor<? extends T> editor = editors.remove(removed.stableId());
			view.dispose(editor);
		}
		return removed;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null) return super.remove(o);

		EntityProxyId<?> id = null;
		if (o instanceof EntityProxy) {
			id = ((EntityProxy) o).stableId();
		} else if (o instanceof EntityProxyId<?>) {
			id = (EntityProxyId<?>) o;
		} else {
			return false;
		}

		for (int i = 0; i < backingList.size(); i++) {
			T e = backingList.get(i);
			if (id.equals(e.stableId())) {
				return remove(i) != null;
			}
		}
		return false;
	}
}
