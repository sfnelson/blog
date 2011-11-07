package org.sfnelson.blog.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public class BlogPlace extends Place {

	public static final BlogPlace PLACE = new BlogPlace();

	BlogPlace() {
	}

	@Prefix("view")
	public static class Tokenizer implements PlaceTokenizer<BlogPlace> {
		@Override
		public BlogPlace getPlace(String token) {
			return PLACE;
		}

		@Override
		public String getToken(BlogPlace place) {
			return "";
		}
	}
}
