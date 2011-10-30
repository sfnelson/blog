package org.sfnelson.blog.server.domain;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.mongo.DomainObject;

import java.util.AbstractList;
import java.util.Date;
import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
public class Post extends DomainObject implements Entry {

	private final TaskUpdate.SourcesTasks provider;

	public Post(TaskUpdate.SourcesTasks provider) {
		super();
		delta.put("type", "post");
		this.provider = provider;
	}

	public Post(DBObject init, TaskUpdate.SourcesTasks provider) {
		super(init);

		this.provider = provider;
	}

	public String getTitle() {
		return (String) get("title");
	}

	public void setTitle(String value) {
		delta.put("title", value);
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
	public String getContent() {
		return (String) get("content");
	}

	@Override
	public void setContent(String content) {
		delta.put("content", content);
	}

	@SuppressWarnings("unchecked")
	public List<TaskUpdate> getProgress() {
		List<DBObject> progress = (List<DBObject>) get("progress");
		if (progress == null) return Lists.newArrayList();
		else return new UpdateList(progress, provider);
	}

	private static class UpdateList extends AbstractList<TaskUpdate> {

		private final List<DBObject> raw;
		private final TaskUpdate.SourcesTasks provider;

		public UpdateList(List<DBObject> raw, TaskUpdate.SourcesTasks provider) {
			this.raw = raw;
			this.provider = provider;
		}

		@Override
		public TaskUpdate get(int index) {
			return new TaskUpdate(raw.get(index), provider);
		}

		@Override
		public int size() {
			return raw.size();
		}
	}
}
