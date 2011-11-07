package org.sfnelson.blog.server.service;

import org.sfnelson.blog.server.domain.Content;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 4/11/11
 */
public interface ContentService {
	public void createContent(Content content);

	public void updateContent(Content content);

	public void deleteContent(Content content);
}
