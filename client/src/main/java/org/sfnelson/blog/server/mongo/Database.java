package org.sfnelson.blog.server.mongo;

import com.google.inject.Inject;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.domain.Entry;
import org.sfnelson.blog.server.domain.Post;
import org.sfnelson.blog.server.domain.Task;
import org.sfnelson.blog.server.domain.TaskUpdate;
import org.sfnelson.blog.server.mongo.DomainObject;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 18/10/11
 */
public class Database {

	private final DB db;

	@Inject
	Database(Mongo mongo) {
		this.db = mongo.getDB("blog");
	}

	public DBCursor findAllTasks() {
		return getCollection(Task.class).find();
	}

	public DBCursor findActiveTasks() {
		DBObject query = new BasicDBObject("completed", null)
				.append("abandoned", null);
		DBObject orderBy = new BasicDBObject("created", -1);
		return getCollection(Task.class).find(query).sort(orderBy);
	}

	public DBCursor findPosts() {
		DBObject query = new BasicDBObject("type", "post");
		DBObject orderBy = new BasicDBObject("posted", -1);
		return getCollection(Post.class).find(query).sort(orderBy);
	}

	public DBCursor findEntries() {
		DBObject orderBy = new BasicDBObject("posted", -1);
		return getCollection(Post.class).find().sort(orderBy);
	}

	public DBObject find(Class<? extends DomainObject> type, ObjectId id) {
		return getCollection(type).findOne(new BasicDBObject("_id", id));
	}

	public void persist(Object object) {
		DomainObject o = (DomainObject) object;
		o.delta.append("version", 1);
		getCollection(o.getClass()).insert(o.delta);
	}

	public void update(Object object) {
		DomainObject o = (DomainObject) object;
		BasicDBObject update = new BasicDBObject();
		update.append("$set", o.delta);
		update.append("$inc", new BasicDBObject("version", 1));
		getCollection(o.getClass()).update(o.init, update);
	}

	public void remove(Object object) {
		DomainObject o = (DomainObject) object;
		getCollection(o.getClass()).remove(o.init);
	}

	private DBCollection getCollection(Class<? extends DomainObject> type) {
		if (type == Task.class) {
			return db.getCollection("tasks");
		}
		if (type == Post.class) {
			return db.getCollection("entries");
		}
		if (type == TaskUpdate.class) {
			return db.getCollection("entries");
		}

		throw new UnsupportedOperationException("don't know how to map " + type);
	}
}
