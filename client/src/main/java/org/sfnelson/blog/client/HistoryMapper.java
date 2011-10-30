package org.sfnelson.blog.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import org.sfnelson.blog.client.places.AuthPlace;
import org.sfnelson.blog.client.places.EntryPlace;
import org.sfnelson.blog.client.places.ManagerPlace;
import org.sfnelson.blog.client.places.TaskPlace;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
@WithTokenizers({
		ManagerPlace.Tokenizer.class,
		EntryPlace.Tokenizer.class,
		TaskPlace.Tokenizer.class,
		AuthPlace.Tokenizer.class
})
public interface HistoryMapper extends PlaceHistoryMapper {
}
