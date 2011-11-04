package org.sfnelson.blog.server.domain;

import com.google.inject.Inject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.mongo.DomainObject;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class Task extends DomainObject<Task> implements org.sfnelson.blog.domain.Task {

	private final DomainObjectLocator locator;

	@Inject
	Task(DomainObjectLocator locator) {
		this.locator = locator;
	}

	public String getTitle() {
		return (String) get("title");
	}

	public void setTitle(String title) {
		delta.put("title", title);
	}

	public Author getOwner() {
		ObjectId id = (ObjectId) get("owner");
		if (id == null) return null;
		else return locator.find(Author.class, id);
	}

	public void setOwner(Author author) {
		delta.put("owner", author.getId());
	}

	public Content getContent() {
		ObjectId id = (ObjectId) get("content");
		if (id == null) return null;
		return locator.find(Content.class, id);
	}

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

	public Date getCreated() {
		return (Date) get("created");
	}

	public void setCreated(Date created) {
		delta.put("created", created);
	}

	public Date getLastUpdate() {
		return (Date) get("updated");
	}

	public void setLastUpdate(Date updated) {
		delta.put("updated", updated);
	}

	public Date getCompleted() {
		return (Date) get("completed");
	}

	public void setCompleted(Date date) {
		delta.put("completed", date);
	}

	public Date getAbandoned() {
		return (Date) get("abandoned");
	}

	public void setAbandoned(Date date) {
		delta.put("abandoned", date);
	}
}
