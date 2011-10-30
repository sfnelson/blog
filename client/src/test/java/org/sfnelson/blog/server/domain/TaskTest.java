package org.sfnelson.blog.server.domain;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskTest {

	private final ObjectId id = new ObjectId();
	private final Integer version = 1;
	private final String title = "Title";
	private final String description = "Description";
	private final Date created = new Date();
	private final Date updated = new Date();
	private final Date completed = new Date();
	private final Date abandoned = new Date();

	private Task task;

	@Before
	public void setUp() throws Exception {
		BasicDBObject init = new BasicDBObject();
		init.put("_id", id);
		init.put("version", version);
		init.put("title", title);
		init.put("description", description);
		init.put("created", created);
		init.put("updated", updated);
		init.put("completed", completed);
		init.put("abandoned", abandoned);
		task = new Task(init);
	}

	@Test
	public void testGetId() {
		assertEquals(id, task.getId());
	}

	@Test
	public void testGetVersion() {
		assertEquals(version, task.getVersion());
	}

	@Test
	public void testGetTitle() throws Exception {
		assertEquals(title, task.getTitle());
	}

	@Test
	public void testGetDescription() throws Exception {
		assertEquals(description, task.getDescription());
	}

	@Test
	public void testGetCreated() throws Exception {
		assertEquals(created, task.getCreated());
	}

	@Test
	public void testGetUpdated() throws Exception {
		assertEquals(updated, task.getUpdated());
	}

	@Test
	public void testGetCompleted() throws Exception {
		assertEquals(completed, task.getCompleted());
	}

	@Test
	public void testGetAbandoned() throws Exception {
		assertEquals(abandoned, task.getAbandoned());
	}

	@Test
	public void testDefaultConstructor() throws Exception {
		Task task = new Task();
		task.setTitle(title);
		assertEquals(title, task.getTitle());
		task.setDescription(description);
		assertEquals(description, task.getDescription());
		task.setCreated(created);
		assertEquals(created, task.getCreated());
		task.setUpdated(updated);
		assertEquals(updated, task.getUpdated());
		task.setCompleted(completed);
		assertEquals(completed, task.getCompleted());
		task.setAbandoned(abandoned);
		assertEquals(abandoned, task.getAbandoned());
	}
}
