package org.sfnelson.blog.server.domain;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.sfnelson.blog.server.DomainObjectLocator;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class PostTest {

	private DomainObjectLocator locator;
	private Post post;
	private final ObjectId id = new ObjectId();
	private final Integer version = 1;
	private final String title = "Title";
	private final Date posted = new Date();
	private final String content = "Content";
	private final ObjectId progress = new ObjectId();

	@Before
	public void setUp() throws Exception {
		BasicDBObject init = new BasicDBObject();
		init.put("_id", id);
		init.put("version", version);
		init.put("title", title);
		init.put("posted", posted);
		init.put("content", content);
		init.put("progress", Lists.<DBObject> newArrayList(new BasicDBObject("_id", progress)));
		locator = EasyMock.createMock(DomainObjectLocator.class);
		post = new Post(init, locator);
	}

	@Test
	public void testGetId() throws Exception {
		assertEquals(id, post.getId());
	}

	@Test
	public void testGetVersion() throws Exception {
		assertEquals(version, post.getVersion());
	}

	@Test
	public void testGetTitle() throws Exception {
		assertEquals(title, post.getTitle());
		post.setTitle("New Title");
		assertEquals("New Title", post.getTitle());
	}

	@Test
	public void testGetPosted() throws Exception {
		assertEquals(posted, post.getPosted());
		Date newPosted = new Date();
		post.setPosted(newPosted);
		assertEquals(newPosted, post.getPosted());
	}

	@Test
	public void testGetContent() throws Exception {
		assertEquals(content, post.getContent());
		post.setContent("New Content");
		assertEquals("New Content", post.getContent());
	}

	@Test
	public void testGetProgress() throws Exception {
		assertEquals(1, post.getProgress().size());
		assertEquals(progress, post.getProgress().get(0).getId());
	}

	@Test
	public void testDefaultConstructor() {
		Post post = new Post(locator);
		post.setTitle(title);
		post.setPosted(posted);
		post.setContent(content);
		assertEquals(null, post.getId());
		assertEquals(null, post.getVersion());
		assertEquals(title, post.getTitle());
		assertEquals(posted, post.getPosted());
		assertEquals(content, post.getContent());
		assertEquals(Lists.newArrayList(), post.getProgress());
	}
}
