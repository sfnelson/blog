package org.sfnelson.blog.server;

import com.google.web.bindery.requestfactory.shared.Locator;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.sfnelson.blog.server.domain.*;
import org.sfnelson.blog.server.mongo.Database;
import org.sfnelson.blog.server.mongo.DomainObject;
import org.sfnelson.blog.server.service.ContentService;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class DomainObjectLocator extends Locator<DomainObject, String> {

	private final Database database;
	private final Provider<Post> posts;
	private final Provider<Task> tasks;
	private final Provider<Update> updates;
	private final Provider<Author> authors;
	private final Provider<Content> content;
	private final ContentService contentService;

	@Inject
	DomainObjectLocator(Database database, Provider<Post> posts, Provider<Task> tasks,
						Provider<Update> updates, Provider<Author> authors,
						Provider<Content> content, ContentService contentService) {
		this.database = database;
		this.posts = posts;
		this.tasks = tasks;
		this.updates = updates;
		this.authors = authors;
		this.content = content;
		this.contentService = contentService;
	}

	@Override
	public DomainObject create(Class<? extends DomainObject> clazz) {
		if (clazz == Post.class) {
			return posts.get();
		} else if (clazz == Task.class) {
			return tasks.get();
		} else if (clazz == Update.class) {
			return updates.get();
		} else if (clazz == Author.class) {
			return authors.get();
		} else if (clazz == Content.class) {
			return content.get();
		} else {
			throw new ClassCastException("unknown type: " + clazz);
		}
	}

	@Override
	public DomainObject find(Class<? extends DomainObject> clazz, String id) {
		return find(clazz, ObjectId.massageToObjectId(id));
	}

	@SuppressWarnings("unchecked")
	public <T extends DomainObject> T find(Class<T> clazz, ObjectId id) {
		DBObject init = database.find(clazz, id);
		if (init == null) {
			return null;
		}
		return (T) create(clazz).init(init);
	}

	@Override
	public Class<DomainObject> getDomainType() {
		return DomainObject.class;
	}

	@Override
	public String getId(DomainObject domainObject) {
		ObjectId id = (ObjectId) domainObject.getId();
		if (id != null) {
			return id.toString();
		} else {
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

	public ContentService getContentService() {
		return contentService;
	}
}
