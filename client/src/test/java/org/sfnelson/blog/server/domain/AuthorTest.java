package org.sfnelson.blog.server.domain;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 31/10/11
 */
public class AuthorTest {

	private final ObjectId id = new ObjectId();
	private final Integer version = 1;
	private final String username = "user";
	private final String email = "user@test.com";
	private final String openId = "sbiubaegf87q93ub5q";

	private Author author;

	@Before
	public void setUp() throws Exception {
		BasicDBObject init = new BasicDBObject();
		init.put("_id", id);
		init.put("version", version);
		init.put("username", username);
		init.put("email", email);
		init.put("openid", openId);
		author = new Author().init(init);
	}

	@Test
	public void testGetUsername() throws Exception {
		assertEquals(username, author.getUsername());
	}

	@Test
	public void testSetUsername() throws Exception {
		author = new Author();
		assertEquals(null, author.getUsername());
		author.setUsername(username);
		assertEquals(username, author.getUsername());
	}

	@Test
	public void testGetOpenId() throws Exception {
		assertEquals(openId, author.getOpenId());
	}

	@Test
	public void testSetOpenId() throws Exception {
		author = new Author();
		assertEquals(null, author.getOpenId());
		author.setOpenId(openId);
		assertEquals(openId, author.getOpenId());
	}

	@Test
	public void testGetEmail() throws Exception {
		assertEquals(email, author.getEmail());
	}

	@Test
	public void testSetEmail() throws Exception {
		author = new Author();
		assertEquals(null, author.getEmail());
		author.setEmail(email);
		assertEquals(email, author.getEmail());
	}

	@Test
	public void testGetId() throws Exception {
		assertEquals(id, author.getId());
	}

	@Test
	public void testGetVersion() throws Exception {
		assertEquals(version, author.getVersion());
	}
}
