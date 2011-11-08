package org.sfnelson.blog.server;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

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
import org.sfnelson.blog.server.domain.Entry;
import org.sfnelson.blog.server.domain.Post;
import org.sfnelson.blog.server.domain.Task;
import org.sfnelson.blog.server.domain.Update;
import org.sfnelson.blog.shared.content.render.ContentRenderer;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
@Singleton
public class RssServlet extends HttpServlet {

	private final EntryManager entryManager;
	private final ContentRenderer renderer;

	@Inject
	RssServlet(EntryManager entryManager) {
		this.entryManager = entryManager;
		this.renderer = new ContentRenderer();
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

		Post previous = null;
		List<Update> complete = Lists.newArrayList();
		List<Update> progress = Lists.newArrayList();

		for (Entry entry : entryManager.getEntries(0, 10)) {
			if (entry instanceof Post) {
				if (previous != null) {
					entries.add(createEntry(previous, complete, progress));
				}
				complete.clear();
				progress.clear();
				previous = (Post) entry;
			} else if (entry instanceof Update) {
				Update update = (Update) entry;
				switch (update.getType()) {
					case PROGRESS:
						progress.add(update);
						break;
					case COMPLETED:
						complete.add(update);
						break;
				}
			}
		}
		if (previous != null) {
			entries.add(createEntry(previous, complete, progress));
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

	private SyndEntry createEntry(Post post, List<Update> complete, List<Update> progress) {
		SyndEntry feedEntry = new SyndEntryImpl();
		feedEntry.setTitle(post.getTitle());
		feedEntry.setPublishedDate(post.getPosted());
		SyndContent content = new SyndContentImpl();
		content.setType("text/html");

		SafeHtmlBuilder value = new SafeHtmlBuilder();
		value.append(renderer.render(post.getContent()));
		if (!complete.isEmpty() || !progress.isEmpty()) {
			value.append(SafeHtmlUtils.fromTrustedString("<h2>Goals</h2>"));
			if (!complete.isEmpty()) {
				value.append(SafeHtmlUtils.fromTrustedString("<h3>Completed:</h3>"));
				value.append(SafeHtmlUtils.fromTrustedString("<dl>"));
				for (Update update : complete) {
					Task task = update.getTask();
					value.append(SafeHtmlUtils.fromTrustedString("<dt>"));
					value.appendEscaped(task.getTitle());
					value.append(SafeHtmlUtils.fromTrustedString("</dt><dd>"));
					value.append(renderer.render(update.getContent()));
					value.append(SafeHtmlUtils.fromTrustedString("</dd>"));
				}
				value.append(SafeHtmlUtils.fromTrustedString("</dl>"));
			}
			if (!progress.isEmpty()) {
				value.append(SafeHtmlUtils.fromTrustedString("<h3>Progress:</h3>"));
				value.append(SafeHtmlUtils.fromTrustedString("<dl>"));
				for (Update update : progress) {
					Task task = update.getTask();
					value.append(SafeHtmlUtils.fromTrustedString("<dt>"));
					value.appendEscaped(task.getTitle());
					value.append(SafeHtmlUtils.fromTrustedString("</dt><dd>"));
					value.append(renderer.render(update.getContent()));
					value.append(SafeHtmlUtils.fromTrustedString("</dd>"));
				}
				value.append(SafeHtmlUtils.fromTrustedString("</dl>"));
			}
		}
		content.setValue(value.toSafeHtml().asString());
		feedEntry.setDescription(content);

		return feedEntry;
	}
}