package org.sfnelson.blog.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.sfnelson.blog.client.activities.ShowAdminNav;
import org.sfnelson.blog.client.places.AuthorPlace;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class NavViewMapper implements ActivityMapper {

	private final Provider<ShowAdminNav> auth;

	@Inject
	NavViewMapper(Provider<ShowAdminNav> auth) {
		this.auth = auth;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AuthorPlace) {
			return auth.get();
		}
		return null;
	}
}
