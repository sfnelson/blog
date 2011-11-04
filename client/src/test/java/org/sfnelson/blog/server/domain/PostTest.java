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
	private final ObjectId contentId = new ObjectId();
	private final Content content = new Content().init(new BasicDBObject("_id", contentId));
	private final ObjectId progress = new ObjectId();
	private final ObjectId authorId = new ObjectId();
	private final Author author = new Author().init(new BasicDBObject("_id", authorId));

	@Before
	public void setUp() throws Exception {
		BasicDBObject init = new BasicDBObject();
		init.put("_id", id);
		init.put("version", version);
		init.put("title", title);
		init.put("posted", posted);
		init.put("author", authorId);
		init.put("content", contentId);
		init.put("progress", Lists.<DBObject> newArrayList(new BasicDBObject("_id", progress)));
		locator = EasyMock.createMock(DomainObjectLocator.class);
		post = new Post(locator).init(init);
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
	}

	@Test
	public void testSetTitle() throws Exception {
		post = new Post(locator);
		assertEquals(null, post.getTitle());
		post.setTitle(title);
		assertEquals(title, post.getTitle());
	}

	@Test
	public void testGetPosted() throws Exception {
		assertEquals(posted, post.getPosted());
	}

	@Test
	public void testSetPosted() throws Exception {
		post = new Post(locator);
		assertEquals(null, post.getPosted());
		post.setPosted(posted);
		assertEquals(posted, post.getPosted());
	}

	@Test
	public void testGetAuthor() throws Exception {
		EasyMock.expect(locator.find(Author.class, authorId)).andReturn(author);
		EasyMock.replay(locator);
		assertEquals(author, post.getAuthor());
		EasyMock.verify(locator);
	}

	@Test
	public void testSetAuthor() throws Exception {
		post = new Post(locator);
		EasyMock.replay(locator);
		assertEquals(null, post.getAuthor());
		EasyMock.verify(locator);
		EasyMock.reset(locator);

		post.setAuthor(author);

		EasyMock.expect(locator.find(Author.class, authorId)).andReturn(author);
		EasyMock.replay(locator);
		assertEquals(author, post.getAuthor());
		EasyMock.verify(locator);
	}

	@Test
	public void testGetContent() throws Exception {
		EasyMock.expect(locator.find(Content.class, contentId)).andReturn(content);
		EasyMock.replay(locator);
		assertEquals(content, post.getContent());
		EasyMock.verify(locator);
	}

	@Test
	public void testSetContent() throws Exception {
		post = new Post(locator);
		EasyMock.replay(locator);
		assertEquals(null, post.getContent());
		EasyMock.verify(locator);
		EasyMock.reset(locator);

		post.setContent(content);

		EasyMock.expect(locator.find(Content.class, contentId)).andReturn(content);
		EasyMock.replay(locator);
		assertEquals(content, post.getContent());
		EasyMock.verify(locator);
	}
}
