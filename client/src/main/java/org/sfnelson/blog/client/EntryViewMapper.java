package org.sfnelson.blog.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.activities.ShowBlog;
import org.sfnelson.blog.client.editors.SelectableEditor;
import org.sfnelson.blog.client.ioc.Author;
import org.sfnelson.blog.client.places.AuthorPlace;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class EntryViewMapper implements ActivityMapper, EditorMapper {

	private final Provider<ShowBlog> viewer;

	private final Provider<ShowBlog> editor;

	private ShowBlog current;

	@Inject
	EntryViewMapper(Provider<ShowBlog> viewer, @Author Provider<ShowBlog> editor) {
		this.viewer = viewer;
		this.editor = editor;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AuthorPlace) {
			current = editor.get();
		} else {
			current = viewer.get();
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
