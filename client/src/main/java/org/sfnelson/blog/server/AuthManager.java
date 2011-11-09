package org.sfnelson.blog.server;

import java.security.MessageDigest;
import java.util.List;

import com.google.gwt.user.server.Base64Utils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.sfnelson.blog.server.domain.Auth;
import org.sfnelson.blog.server.mongo.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 29/10/11
 */
public class AuthManager {

	private final ConsumerManager manager;
	private final Database database;
	private final Provider<Auth> authProvider;
	private final MessageDigest digest;

	private final Logger log = LoggerFactory.getLogger(AuthManager.class);

	@Inject
	AuthManager(Database database, Provider<Auth> authProvider) throws Exception {
		this.manager = new ConsumerManager();
		this.database = database;
		this.authProvider = authProvider;
		this.digest = MessageDigest.getInstance("MD5");
	}

	public Auth login(String authString, String returnURL) throws DiscoveryException, MessageException, ConsumerException {
		logout();

		// String url = RequestFactoryServlet.getThreadLocalRequest().getRequestURL().toString();
		// url = url.substring(0, url.indexOf("gwtRequest"));
		String url = returnURL;
		if (url.indexOf('#') > 0) url = url.substring(0, url.indexOf('#'));
		if (url.indexOf('/') > 0) url = url.substring(0, url.lastIndexOf('/'));
		url += "/oauth";

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
		String url = (String) req.getSession().getAttribute("openid-returnURL");
		if (url.indexOf('#') > 0) url = url.substring(0, url.indexOf('#'));
		if (url.indexOf('/') > 0) url = url.substring(0, url.lastIndexOf('/'));
		StringBuffer receivingURL = new StringBuffer().append(url);
		receivingURL.append("/oauth");
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
		if (verified != null) {
			AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();
			byte[] idHash = digest.digest(authSuccess.getIdentity().getBytes());
			String id = Base64Utils.toBase64(idHash);

			req.getSession().setAttribute("oauth-id", id);

			Auth auth = authProvider.get().init(database.find(Auth.class, id));
			if (!auth.getAuthenticated()) {
				auth.setAuthor(false);
				log.info("new user registered ({})", id);
				database.persist(auth, id);
				auth = authProvider.get().init(database.find(Auth.class, id));
			}

			if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
				FetchResponse fetchResp = (FetchResponse) authSuccess
						.getExtension(AxMessage.OPENID_NS_AX);

				List emails = fetchResp.getAttributeValues("email");
				String email = (String) emails.get(0);
				if (auth.getEmail() == null || !auth.getEmail().equals(email)) {
					auth.setEmail(email);
					log.info("updating email for {} - {}", id, email);
					database.update(auth);
				}
			}

			log.info("{} is now logged in", auth.getEmail());

			return (String) req.getSession().getAttribute("openid-returnURL");  // success
		}

		log.error("could not retrieve verification identifier, failing");
		return null;
	}

	public Auth cookie(String oauthId) {
		String id = (String) getSession().getAttribute("oauth-id");

		if (id == null) {
			Auth auth = getAuth(oauthId);
			if (auth.getAuthenticated()) {
				getSession().setAttribute("oauth-id", oauthId);

				log.info("{} logged in using a stored cookie", auth.getEmail());
			}
		}

		return getAuth(id);
	}

	public Auth state() {
		HttpSession session = getSession();
		String id = (String) session.getAttribute("oauth-id");

		if (id != null) {
			return getAuth(id);
		}

		for (Cookie cookie : getRequest().getCookies()) {
			if (cookie.getName().equals("oauth-id")) {
				return cookie(cookie.getValue());
			}
		}

		return authProvider.get().init(null);
	}

	public Auth logout() {
		HttpSession session = getSession();
		Auth state = state();

		if (state != null) {
			log.info("{} logged out", state.getEmail());
		}

		session.setAttribute("oauth-id", null);
		session.setAttribute("oauth-email", null);
		return authProvider.get().init(null);
	}

	private HttpSession getSession() {
		return RequestFactoryServlet.getThreadLocalRequest().getSession();
	}

	private HttpServletRequest getRequest() {
		return RequestFactoryServlet.getThreadLocalRequest();
	}

	private Auth getAuth(String id) {
		return authProvider.get().init(database.find(Auth.class, id));
	}
}
