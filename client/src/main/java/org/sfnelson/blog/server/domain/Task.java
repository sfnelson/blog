package org.sfnelson.blog.server.domain;

import com.mongodb.DBObject;
import org.sfnelson.blog.server.mongo.DomainObject;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class Task extends DomainObject {

	public Task() {
		super();
	}

	public Task(DBObject init) {
		super(init);
	}

	public String getTitle() {
		return (String) get("title");
	}

	public void setTitle(String title) {
		delta.put("title", title);
	}

	public String getDescription() {
		return (String) get("description");
	}

	public void setDescription(String description) {
		delta.put("description", description);
	}

	public Date getCreated() {
		return (Date) get("created");
	}

	public void setCreated(Date created) {
		delta.put("created", created);
	}

	public Date getUpdated() {
		return (Date) get("updated");
	}

	public void setUpdated(Date updated) {
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
