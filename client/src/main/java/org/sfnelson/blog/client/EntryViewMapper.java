package org.sfnelson.blog.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

import com.google.inject.Inject;
import org.sfnelson.blog.client.editors.SelectableEditor;
import org.sfnelson.blog.client.places.AuthorPlace;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class EntryViewMapper implements ActivityMapper, EditorMapper {

	private final ShowBlog blogActivity;

	@Inject
	EntryViewMapper(ShowBlog blogActivity) {
		this.blogActivity = blogActivity;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AuthorPlace) {
			return blogActivity;
		} else {
			return blogActivity;
		}
	}

	@Override
	public EntityProxyId<?> getPrevious(EntityProxyId<?> current) {
		return blogActivity.getEditorMapper().getPrevious(current);
	}

	@Override
	public SelectableEditor getEditor(EntityProxyId<?> entity) {
		return blogActivity.getEditorMapper().getEditor(entity);
	}

	@Override
	public EntityProxyId<?> getNext(EntityProxyId<?> current) {
		return blogActivity.getEditorMapper().getNext(current);
	}
}
