package org.sfnelson.blog.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 30/10/11
 */
public class AuthPlace extends Place {

	private final String action;

	public AuthPlace(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public String toString() {
		return "auth:" + action;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		AuthPlace p = (AuthPlace) obj;
		if (action == null) return p.action == null;
		return action.equals(p.action);
	}

	public int hashCode() {
		return action == null ? 0 : getClass().hashCode() ^ action.hashCode();
	}

	@Prefix("auth")
	public static class Tokenizer implements PlaceTokenizer<AuthPlace> {

		@Override
		public AuthPlace getPlace(String token) {
			return new AuthPlace(token);
		}

		@Override
		public String getToken(AuthPlace place) {
			return place.getAction();
		}
	}
}