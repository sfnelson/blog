package org.sfnelson.blog.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.openid4java.OpenIDException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
@Singleton
public class OAuthServlet extends HttpServlet {

	private final AuthManager auth;

	@Inject
	OAuthServlet(AuthManager auth) {
		this.auth = auth;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String returnURL = auth.verify(req);
			if (returnURL.indexOf('#') > 0) {
				returnURL = returnURL.substring(0, returnURL.indexOf('#'));
			}

			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/html");
			resp.getWriter().append("<html><head><title>Done</title>" +
				"<script>window.opener.location.href='" + returnURL + "#auth:ready';</script>" +
				"</head></html>\r\n\r\n");
		}
		catch (OpenIDException ex) {
			throw new ServletException(ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
