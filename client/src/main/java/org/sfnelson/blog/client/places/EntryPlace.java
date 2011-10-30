package org.sfnelson.blog.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public class EntryPlace extends Place {

	private final String action;

	public EntryPlace(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public String toString() {
		return "entry:" + action;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EntryPlace p = (EntryPlace) obj;
		if (action == null) return p.action == null;
		return action.equals(p.action);
	}

	public int hashCode() {
		return action == null ? 0 : getClass().hashCode() ^ action.hashCode();
	}

	@Prefix("entry")
	public static class Tokenizer implements PlaceTokenizer<EntryPlace> {

		@Override
		public EntryPlace getPlace(String token) {
			return new EntryPlace(token);
		}

		@Override
		public String getToken(EntryPlace place) {
			return place.getAction();
		}
	}
}
