package org.sfnelson.blog.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.activities.ShowTasks;
import org.sfnelson.blog.client.editors.SelectableEditor;
import org.sfnelson.blog.client.places.AuthorPlace;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class TaskViewMapper implements ActivityMapper, EditorMapper {

	private final Provider<ShowTasks> tasks;

	private ShowTasks current;

	@Inject
	TaskViewMapper(Provider<ShowTasks> tasks) {
		this.tasks = tasks;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AuthorPlace) {
			if (current == null) {
				current = tasks.get();
			}
		} else {
			current = null;
		}
		return current;
	}

	@Override
	public EntityProxyId<?> getPrevious(EntityProxyId<?> current) {
		if (this.current == null) return null;
		return this.current.getEditorMapper().getPrevious(current);
	}

	@Override
	public SelectableEditor getEditor(EntityProxyId<?> entity) {
		if (this.current == null) return null;
		return this.current.getEditorMapper().getEditor(entity);
	}

	@Override
	public EntityProxyId<?> getNext(EntityProxyId<?> current) {
		if (this.current == null) return null;
		return this.current.getEditorMapper().getNext(current);
	}
}
