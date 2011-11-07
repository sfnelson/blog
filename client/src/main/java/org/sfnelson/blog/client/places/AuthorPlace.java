package org.sfnelson.blog.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 30/10/11
 */
public class AuthorPlace extends Place {

	private final String action;

	public AuthorPlace(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public String toString() {
		return "author:" + action;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		AuthorPlace p = (AuthorPlace) obj;
		if (action == null) return p.action == null;
		return action.equals(p.action);
	}

	public int hashCode() {
		return action == null ? 0 : getClass().hashCode() ^ action.hashCode();
	}

	@Prefix("publish")
	public static class Tokenizer implements PlaceTokenizer<AuthorPlace> {

		@Override
		public AuthorPlace getPlace(String token) {
			return new AuthorPlace(token);
		}

		@Override
		public String getToken(AuthorPlace place) {
			return place.getAction();
		}
	}
}