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
public class TaskUpdateTest {

	private final ObjectId id = new ObjectId();
	private final Integer version = 1;
	private final String message = "Message";
	private final Date created = new Date();
	private final ObjectId taskId = new ObjectId();
	private final Task task = new Task(new BasicDBObject("_id", taskId));
	private final TaskUpdateType type = TaskUpdateType.PROGRESS;

	private DomainObjectLocator locator;
	private TaskUpdate update;

	@Before
	public void setUp() throws Exception {
		BasicDBObject init = new BasicDBObject();
		init.put("_id", id);
		init.put("version", version);
		init.put("message", message);
		init.put("created", created);
		init.put("task", taskId);
		init.put("type", type.name());
		locator = EasyMock.createMock(DomainObjectLocator.class);
		update = new TaskUpdate(init, locator);
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
		update = new TaskUpdate(locator);
		update.setPosted(created);
		assertEquals(created, update.getPosted());
	}

	@Test
	public void testGetMessage() throws Exception {
		assertEquals(message, update.getContent());
	}

	@Test
	public void testSetMessage() throws Exception {
		update = new TaskUpdate(locator);
		update.setContent(message);
		assertEquals(message, update.getContent());
	}

	@Test
	public void testGetTask() throws Exception {
		EasyMock.expect(locator.getTask(taskId)).andReturn(task);
		EasyMock.replay(locator);
		assertEquals(task, update.getTask());
		EasyMock.verify(locator);
	}

	@Test
	public void testSetTask() throws Exception {
		update = new TaskUpdate(locator);
		update.setTask(task);

		EasyMock.expect(locator.getTask(taskId)).andReturn(task);
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
		update = new TaskUpdate(locator);
		update.setType(TaskUpdateType.CREATED);
		assertEquals(TaskUpdateType.CREATED, update.getType());
		update.setType(TaskUpdateType.PROGRESS);
		assertEquals(TaskUpdateType.PROGRESS, update.getType());
		update.setType(TaskUpdateType.COMPLETED);
		assertEquals(TaskUpdateType.COMPLETED, update.getType());
		update.setType(TaskUpdateType.ABANDONED);
		assertEquals(TaskUpdateType.ABANDONED, update.getType());
	}
}
