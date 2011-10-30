package org.sfnelson.blog.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public class ManagerPlace extends Place {

	public static final ManagerPlace PLACE = new ManagerPlace();

	ManagerPlace() {}

	@Prefix("manage")
	public static class Tokenizer implements PlaceTokenizer<ManagerPlace> {
		@Override
		public ManagerPlace getPlace(String token) {
			return PLACE;
		}

		@Override
		public String getToken(ManagerPlace place) {
			return "";
		}
	}
}
