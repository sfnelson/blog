package org.sfnelson.blog.server.domain;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.domain.Comment;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.mongo.DomainObject;

import java.util.AbstractList;
import java.util.Date;
import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
public class Post extends DomainObject<Post> implements Entry, org.sfnelson.blog.domain.Post {

	private final DomainObjectLocator locator;

	@Inject
	Post(DomainObjectLocator locator) {
		this.locator = locator;
		delta.put("type", "post");
	}

	public String getTitle() {
		return (String) get("title");
	}

	public void setTitle(String value) {
		delta.put("title", value);
	}

	public Author getAuthor() {
		ObjectId id = (ObjectId) get("author");
		if (id == null) return null;
		return locator.find(Author.class, id);
	}

	public void setAuthor(Author author) {
		ObjectId id = author.getId();
		delta.put("author", id);
	}

	@Override
	public Date getPosted() {
		return (Date) get("posted");
	}

	@Override
	public void setPosted(Date posted) {
		delta.put("posted", posted);
	}

	@Override
	public Content getContent() {
		ObjectId id = (ObjectId) get("content");
		if (id == null) return null;
		return locator.find(Content.class, id);
	}

	@Override
	public void setContent(Content content) {
		if (content == null) {
			delta.put("content", null);
		}
		else {
			if (content.getId() == null) {
				locator.getContentService().createContent(content);
			}
			delta.put("content", content.getId());
		}
	}

	public List<Comment> getComments() {
		return null;
	}
}
