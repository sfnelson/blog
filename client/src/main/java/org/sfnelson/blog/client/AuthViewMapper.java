package org.sfnelson.blog.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import com.google.inject.Inject;
import org.sfnelson.blog.client.activities.ShowAuth;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 8/11/11
 */
public class AuthViewMapper implements ActivityMapper {

	private final ShowAuth auth;

	@Inject
	AuthViewMapper(ShowAuth auth) {
		this.auth = auth;
	}

	@Override
	public Activity getActivity(Place place) {
		return auth.goTo(place);
	}
}
