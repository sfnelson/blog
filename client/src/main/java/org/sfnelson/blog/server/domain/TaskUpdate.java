package org.sfnelson.blog.server.domain;

import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.mongo.DomainObject;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskUpdate extends DomainObject implements Entry {

	public interface SourcesTasks {
		Task getTask(ObjectId id);
	}

	private final SourcesTasks provider;

	public TaskUpdate(SourcesTasks provider) {
		super();
		delta.put("type", "update");
		this.provider = provider;
	}

	public TaskUpdate(DBObject init, SourcesTasks provider) {
		super(init);

		this.provider = provider;
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
	public String getContent() {
		return (String) get("message");
	}

	@Override
	public void setContent(String message) {
		delta.put("message", message);
	}

	public Task getTask() {
		return provider.getTask((ObjectId) get("task"));
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
