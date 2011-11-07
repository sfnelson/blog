package org.sfnelson.blog.server;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sfnelson.blog.server.domain.Post;
import org.sfnelson.blog.shared.content.render.ContentRenderer;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
@Singleton
public class RssServlet extends HttpServlet {

	private final EntryManager entryManager;

	@Inject
	RssServlet(EntryManager entryManager) {
		this.entryManager = entryManager;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("&ldquo;So how is your thesis going?&rdquo;");
		feed.setLink("http://nz.sfnelson.org/blog/");
		feed.setDescription("Some answers to the perennial question. Stephen Nelson's thesis progress blog.");
		feed.setAuthor("Stephen Nelson");
		feed.setPublishedDate(new Date());

		List<SyndEntry> entries = Lists.newArrayList();

		for (Post entry : entryManager.getEntries()) {
			SyndEntry feedEntry = new SyndEntryImpl();
			feedEntry.setTitle(entry.getTitle());
			feedEntry.setPublishedDate(entry.getPosted());
			SyndContent content = new SyndContentImpl();
			content.setType("text/html");
			content.setValue(new ContentRenderer().render(entry.getContent()).asString());
			feedEntry.setDescription(content);
			entries.add(feedEntry);
		}

		feed.setEntries(entries);

		SyndFeedOutput output = new SyndFeedOutput();

		try {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/rss+xml");
			output.output(feed, resp.getWriter());
		} catch (FeedException ex) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}