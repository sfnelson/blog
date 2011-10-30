package org.sfnelson.blog.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
public class TaskPlace extends Place {

	private final String action;

	public TaskPlace(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public String toString() {
		return "task:" + action;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TaskPlace p = (TaskPlace) obj;
		if (action == null) return p.action == null;
		return action.equals(p.action);
	}

	public int hashCode() {
		return action == null ? 0 : getClass().hashCode() ^ action.hashCode();
	}

	@Prefix("task")
	public static class Tokenizer implements PlaceTokenizer<TaskPlace> {

		@Override
		public TaskPlace getPlace(String token) {
			return new TaskPlace(token);
		}

		@Override
		public String getToken(TaskPlace place) {
			return place.getAction();
		}
	}
}
