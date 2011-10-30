package org.sfnelson.blog.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.http.HttpRequest;
import org.apache.http.io.SessionInputBuffer;
import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
public class AuthManager {

	private final ConsumerManager manager;

	@Inject
	AuthManager() {
		manager = new ConsumerManager();
	}

	public Auth login(String authString, String returnURL) throws DiscoveryException, MessageException, ConsumerException {
		String url = RequestFactoryServlet.getThreadLocalRequest().getRequestURL().toString();
		url = url.substring(0, url.indexOf("gwtRequest"));
		url += "oauth";

		List discoveries = manager.discover(authString);
		DiscoveryInformation discovered = manager.associate(discoveries);
		HttpSession session = getSession();
		session.setAttribute("openid-disc", discovered);
		session.setAttribute("openid-returnURL", returnURL);

		AuthRequest authRequest = manager.authenticate(discovered, url);

		FetchRequest fetch = FetchRequest.createFetchRequest();
		fetch.addAttribute("email", "http://schema.openid.net/contact/email", true);
		authRequest.addExtension(fetch);

		return new Auth(authRequest.getDestinationUrl(true));
	}

	public String verify(HttpServletRequest req) throws MessageException, AssociationException, DiscoveryException {
		ParameterList response = new ParameterList(req.getParameterMap());
		DiscoveryInformation discovered = (DiscoveryInformation) req.getSession().getAttribute("openid-disc");

		// extract the receiving URL from the HTTP request
		StringBuffer receivingURL = req.getRequestURL();
		String queryString = req.getQueryString();
		if (queryString != null && queryString.length() > 0)
			receivingURL.append("?").append(req.getQueryString());

		// verify the response; ConsumerManager needs to be the same
		// (static) instance used to place the authentication request
		VerificationResult verification = manager.verify(
				receivingURL.toString(),
				response, discovered);

		// examine the verification result and extract the verified identifier
		Identifier verified = verification.getVerifiedId();
		if (verified != null)
		{
			AuthSuccess authSuccess =
					(AuthSuccess) verification.getAuthResponse();

			if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX))
			{
				FetchResponse fetchResp = (FetchResponse) authSuccess
						.getExtension(AxMessage.OPENID_NS_AX);

				List emails = fetchResp.getAttributeValues("email");
				String email = (String) emails.get(0);
				req.getSession().setAttribute("oauth-email", email);
			}

			return (String) req.getSession().getAttribute("openid-returnURL");  // success
		}

		return null;
	}

	public Auth state() {
		HttpSession session = getSession();
		String email = (String) session.getAttribute("oauth-email");
		if (email == null) {
			return new Auth();
		}
		else {
			return new Auth(email, false);
		}
	}

	public Auth logout() {
		HttpSession session = getSession();
		session.setAttribute("oauth-email", null);
		return new Auth();
	}

	private HttpSession getSession() {
		return RequestFactoryServlet.getThreadLocalRequest().getSession();
	}
}
