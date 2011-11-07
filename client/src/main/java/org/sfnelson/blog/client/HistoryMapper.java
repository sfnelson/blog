package org.sfnelson.blog.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import org.sfnelson.blog.client.places.AuthPlace;
import org.sfnelson.blog.client.places.AuthorPlace;
import org.sfnelson.blog.client.places.BlogPlace;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 26/10/11
 */
@WithTokenizers({
		BlogPlace.Tokenizer.class,
		AuthorPlace.Tokenizer.class,
		AuthPlace.Tokenizer.class
})
public interface HistoryMapper extends PlaceHistoryMapper {
}
