package org.sfnelson.blog.server.domain;

import java.util.Date;

import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.mongo.DomainObject;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class Update extends DomainObject<Update> implements Entry {

	private final DomainObjectLocator locator;

	@Inject
	Update(DomainObjectLocator locator) {
		this.locator = locator;
		delta.put("type", "update");
	}

	@Override
	public Date getPosted() {
		return (Date) get("posted");
	}

	@Override
	public void setPosted(Date created) {
		delta.put("posted", created);
	}

	@Override
	public Author getAuthor() {
		ObjectId id = (ObjectId) get("author");
		if (id == null) return null;
		return locator.find(Author.class, id);
	}

	@Override
	public void setAuthor(Author author) {
		if (author != null) {
			ObjectId id = author.getId();
			delta.put("author", id);
		} else {
			delta.remove("author");
		}
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
		} else {
			if (content.getId() == null) {
				locator.getContentService().createContent(content);
			}
			delta.put("content", content.getId());
		}
	}

	public Task getTask() {
		ObjectId id = (ObjectId) get("task");
		if (id == null) return null;
		return locator.find(Task.class, id);
	}

	public void setTask(Task task) {
		delta.put("task", task.getId());
	}

	public TaskUpdateType getType() {
		return TaskUpdateType.valueOf((String) get("updateType"));
	}

	public void setType(TaskUpdateType type) {
		delta.put("updateType", type.name());
	}
}
