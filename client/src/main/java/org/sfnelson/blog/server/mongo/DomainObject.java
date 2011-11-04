package org.sfnelson.blog.server.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 19/10/11
 */
public class DomainObject<T extends DomainObject<T>> {

	protected DBObject init;
	protected BasicDBObject delta;

	protected DomainObject() {
		init = new BasicDBObject();
		delta = new BasicDBObject();
	}

	@SuppressWarnings("unchecked")
	public T init(DBObject init) {
		this.init = init;
		delta = new BasicDBObject();
		return (T) this;
	}

	public ObjectId getId() {
		return (ObjectId) get("_id");
	}

	public Integer getVersion() {
		return (Integer) get("version");
	}

	protected Object get(String field) {
		if (delta.containsField(field)) return delta.get(field);
		if (init.containsField(field)) return init.get(field);
		return null;
	}
}
