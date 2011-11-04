package org.sfnelson.blog.server.domain;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.sfnelson.blog.server.DomainObjectLocator;
import org.sfnelson.blog.server.service.ContentService;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class TaskTest {

	private final ObjectId id = new ObjectId();
	private final Integer version = 1;
	private final String title = "Title";
	private final ObjectId contentId = new ObjectId();
	private final Content content = new Content().init(new BasicDBObject("_id", contentId));
	private final Date created = new Date();
	private final Date updated = new Date();
	private final Date completed = new Date();
	private final Date abandoned = new Date();
	private final ObjectId authorId = new ObjectId();
	private final Author author = new Author().init(new BasicDBObject("_id", authorId));

	private DomainObjectLocator locator;
	private ContentService contentService;
	private Task task;

	@Before
	public void setUp() throws Exception {
		BasicDBObject init = new BasicDBObject();
		init.put("_id", id);
		init.put("version", version);
		init.put("title", title);
		init.put("content", contentId);
		init.put("created", created);
		init.put("updated", updated);
		init.put("completed", completed);
		init.put("abandoned", abandoned);
		init.put("owner", authorId);
		locator = EasyMock.createMock(DomainObjectLocator.class);
		contentService = EasyMock.createMock(ContentService.class);
		task = new Task(locator).init(init);
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
	public void testSetTitle() throws Exception {
		task = new Task(locator);
		assertEquals(null, task.getTitle());
		task.setTitle(title);
		assertEquals(title, task.getTitle());
	}

	@Test
	public void testGetContent() throws Exception {
		EasyMock.expect(locator.find(Content.class, contentId)).andReturn(content);
		EasyMock.replay(locator);
		assertEquals(content, task.getContent());
		EasyMock.verify(locator);
	}

	@Test
	public void testSetContent() throws Exception {
		task = new Task(locator);
		EasyMock.replay(locator);
		assertEquals(null, task.getContent());
		EasyMock.verify(locator);
		EasyMock.reset(locator);

		Content content = new Content();
		EasyMock.expect(locator.getContentService()).andReturn(contentService);
		contentService.createContent(content);
		EasyMock.replay(locator, contentService);
		task.setContent(content);
		EasyMock.verify(locator, contentService);
		EasyMock.reset(locator, contentService);
	}

	@Test
	public void testGetCreated() throws Exception {
		assertEquals(created, task.getCreated());
	}

	@Test
	public void testSetCreated() throws Exception {
		task = new Task(locator);
		assertEquals(null, task.getCreated());
		task.setCreated(created);
		assertEquals(created, task.getCreated());
	}

	@Test
	public void testGetLastUpdate() throws Exception {
		assertEquals(updated, task.getLastUpdate());
	}

	@Test
	public void testSetLastUpdate() throws Exception {
		task = new Task(locator);
		assertEquals(null, task.getLastUpdate());
		task.setLastUpdate(updated);
		assertEquals(updated, task.getLastUpdate());
	}

	@Test
	public void testGetCompleted() throws Exception {
		assertEquals(completed, task.getCompleted());
	}

	@Test
	public void testSetCompleted() throws Exception {
		task = new Task(locator);
		assertEquals(null, task.getCompleted());
		task.setCompleted(completed);
		assertEquals(completed, task.getCompleted());
	}

	@Test
	public void testGetAbandoned() throws Exception {
		assertEquals(abandoned, task.getAbandoned());
	}

	@Test
	public void testSetAbandoned() throws Exception {
		task = new Task(locator);
		assertEquals(null, task.getAbandoned());
		task.setAbandoned(abandoned);
		assertEquals(abandoned, task.getAbandoned());
	}

	@Test
	public void testGetAuthor() throws Exception {
		EasyMock.expect(locator.find(Author.class, authorId)).andReturn(author);
		EasyMock.replay(locator);
		assertEquals(author, task.getOwner());
		EasyMock.verify(locator);
	}

	@Test
	public void testSetAuthor() throws Exception {
		task = new Task(locator);
		EasyMock.replay(locator);
		assertEquals(null, task.getOwner());
		EasyMock.verify(locator);
		EasyMock.reset(locator);

		task.setOwner(author);

		EasyMock.expect(locator.find(Author.class, authorId)).andReturn(author);
		EasyMock.replay(locator);
		assertEquals(author, task.getOwner());
		EasyMock.verify(locator);
	}
}
