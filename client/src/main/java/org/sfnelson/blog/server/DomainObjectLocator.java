package org.sfnelson.blog.server;

import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.domain.Entry;
import org.sfnelson.blog.server.domain.Post;
import org.sfnelson.blog.server.domain.Task;
import org.sfnelson.blog.server.domain.TaskUpdate;
import org.sfnelson.blog.server.mongo.Database;
import org.sfnelson.blog.server.mongo.DomainObject;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class DomainObjectLocator extends Locator<DomainObject, String> implements TaskUpdate.SourcesTasks {

	private final Database database;

	@Inject
	DomainObjectLocator(Database database) {
		this.database = database;
	}

	@Override
	public DomainObject create(Class<? extends DomainObject> clazz) {
		if (clazz == Post.class) {
			return new Post(this);
		}
		else if (clazz == Task.class) {
			return new Task();
		}
		else if (clazz == TaskUpdate.class) {
			return new TaskUpdate(this);
		}
		else {
			throw new RuntimeException("invalid type: " + clazz);
		}
	}

	@Override
	public DomainObject find(Class<? extends DomainObject> clazz, String id) {
		DBObject init = database.find(clazz, ObjectId.massageToObjectId(id));
		if (init == null) {
			return null;
		}
		if (clazz == Post.class) {
			return new Post(init, this);
		}
		if (clazz == Task.class) {
			return new Task(init);
		}
		if (clazz == TaskUpdate.class) {
			return new TaskUpdate(init, this);
		}
		throw new ClassCastException("unknown type: " + clazz.toString());
	}

	@Override
	public Class<DomainObject> getDomainType() {
		return DomainObject.class;
	}

	@Override
	public String getId(DomainObject domainObject) {
		ObjectId id = domainObject.getId();
		if (id != null) {
			return id.toString();
		}
		else {
			return null;
		}
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(DomainObject domainObject) {
		return domainObject.getVersion();
	}

	@Override
	public Task getTask(ObjectId id) {
		return (Task) find(Task.class, id.toString());
	}
}
