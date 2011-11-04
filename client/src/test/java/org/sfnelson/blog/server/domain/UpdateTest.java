package org.sfnelson.blog.server.domain;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.shared.domain.TaskUpdateType;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class UpdateTest {

	private final ObjectId id = new ObjectId();
	private final Integer version = 1;
	private final ObjectId contentId = new ObjectId();
	private final Content content = new Content().init(new BasicDBObject("_id", contentId));
	private final Date created = new Date();
	private final ObjectId taskId = new ObjectId();
	private final Task task = new Task(null).init(new BasicDBObject("_id", taskId));
	private final TaskUpdateType type = TaskUpdateType.PROGRESS;
	private final ObjectId authorId = new ObjectId();
	private final Author author = new Author().init(new BasicDBObject("_id", authorId));

	private DomainObjectLocator locator;
	private Update update;

	@Before
	public void setUp() throws Exception {
		BasicDBObject init = new BasicDBObject();
		init.put("_id", id);
		init.put("version", version);
		init.put("content", contentId);
		init.put("posted", created);
		init.put("task", taskId);
		init.put("updateType", type.name());
		init.put("author", authorId);
		locator = EasyMock.createMock(DomainObjectLocator.class);
		update = new Update(locator).init(init);
	}

	@Test
	public void testGetId() throws Exception {
		assertEquals(id, update.getId());
	}

	@Test
	public void testGetVersion() throws Exception {
		assertEquals(version, update.getVersion());
	}

	@Test
	public void testGetCreated() throws Exception {
		assertEquals(created, update.getPosted());
	}

	@Test
	public void testSetCreated() throws Exception {
		update = new Update(locator);
		update.setPosted(created);
		assertEquals(created, update.getPosted());
	}

	@Test
	public void testGetContent() throws Exception {
		EasyMock.expect(locator.find(Content.class, contentId)).andReturn(content);
		EasyMock.replay(locator);
		assertEquals(content, update.getContent());
		EasyMock.verify(locator);
	}

	@Test
	public void testSetContent() throws Exception {
		update = new Update(locator);
		EasyMock.replay(locator);
		assertEquals(null, update.getContent());
		EasyMock.verify(locator);
		EasyMock.reset(locator);

		update.setContent(content);

		EasyMock.expect(locator.find(Content.class, contentId)).andReturn(content);
		EasyMock.replay(locator);
		assertEquals(content, update.getContent());
		EasyMock.verify(locator);
	}

	@Test
	public void testGetTask() throws Exception {
		EasyMock.expect(locator.find(Task.class, taskId)).andReturn(task);
		EasyMock.replay(locator);
		assertEquals(task, update.getTask());
		EasyMock.verify(locator);
	}

	@Test
	public void testSetTask() throws Exception {
		update = new Update(locator);
		EasyMock.replay(locator);
		assertEquals(null, update.getTask());
		EasyMock.verify(locator);
		EasyMock.reset(locator);

		update.setTask(task);

		EasyMock.expect(locator.find(Task.class, taskId)).andReturn(task);
		EasyMock.replay(locator);
		assertEquals(task, update.getTask());
		EasyMock.verify(locator);
	}

	@Test
	public void testGetType() throws Exception {
		assertEquals(type, update.getType());
	}

	@Test
	public void testSetType() throws Exception {
		update = new Update(locator);
		update.setType(TaskUpdateType.CREATED);
		assertEquals(TaskUpdateType.CREATED, update.getType());
		update.setType(TaskUpdateType.PROGRESS);
		assertEquals(TaskUpdateType.PROGRESS, update.getType());
		update.setType(TaskUpdateType.COMPLETED);
		assertEquals(TaskUpdateType.COMPLETED, update.getType());
		update.setType(TaskUpdateType.ABANDONED);
		assertEquals(TaskUpdateType.ABANDONED, update.getType());
	}

	@Test
	public void testGetAuthor() throws Exception {
		EasyMock.expect(locator.find(Author.class, authorId)).andReturn(author);
		EasyMock.replay(locator);
		assertEquals(author, update.getAuthor());
		EasyMock.verify(locator);
	}

	@Test
	public void testSetAuthor() throws Exception {
		update = new Update(locator);
		EasyMock.replay(locator);
		assertEquals(null, update.getAuthor());
		EasyMock.verify(locator);
		EasyMock.reset(locator);

		update.setAuthor(author);

		EasyMock.expect(locator.find(Author.class, authorId)).andReturn(author);
		EasyMock.replay(locator);
		assertEquals(author, update.getAuthor());
		EasyMock.verify(locator);
	}

}
