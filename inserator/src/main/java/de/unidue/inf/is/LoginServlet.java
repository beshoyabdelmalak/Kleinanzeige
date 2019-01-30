package de.unidue.inf.is;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.UserStore;

/**
 * Einfaches Beispiel, das die Verwendung des {@link UserStore}s zeigt.
 */
public final class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static List<String> userList = new ArrayList<>();
	private boolean loginStatus = false;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession oldSession = request.getSession(false);
		// zugriff aud die login seite von dem angemeldten anwneder nicht moglich.
		if (oldSession == null) {
			request.setAttribute("navtype", "general");
			request.setAttribute("navtype1", "general");
			if (loginStatus) {
				// im folgenden ist das eine fallunterscheidung, ob das request login oder
				// register
				if (request.getParameter("which").equals("login"))
					request.setAttribute("navtype", "false");
				else
					request.setAttribute("navtype1", "false");

				loginStatus = false;
			}
			request.getRequestDispatcher("/login.ftl").forward(request, response);
		} else {
			response.sendRedirect("hauptseite");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// im folgenden ist das eine fallunterscheidung, ob das request login oder
		// register
		if (request.getParameter("which").equals("login")) {
			String benutzername = request.getParameter("Benutzername");
			UserStore user = new UserStore();
			boolean fehlerhafteAnmeldung = true;
			userList = user.getUserNames();
			for (String u : userList) {
				if (benutzername.equals(u)) {
					// session erstellen um der anwender zu merken
					HttpSession session = request.getSession();
					session.setAttribute("benutzername", benutzername);
					response.sendRedirect("hauptseite");
					fehlerhafteAnmeldung = false;
				}
			}
			user.complete();
			user.close();
			if (fehlerhafteAnmeldung) {
				// bemerken ein fehlerhaft login
				loginStatus = true;
				doGet(request, response);
			}
		} else {
			String benutzername = request.getParameter("Benutzername");
			String name = request.getParameter("name");
			UserStore user = new UserStore();
			userList = user.getUserNames();
			boolean vohandeneRegistrierteBenutzername = false;
			for (String u : userList) {
				if (benutzername.equals(u)) {
					vohandeneRegistrierteBenutzername = true;
				}
			}
			if (!vohandeneRegistrierteBenutzername && benutzername.length()<= 20  && !benutzername.isEmpty() && name.length() <= 50
					&& !name.isEmpty()) {
				
				User newUser = new User(escapeHtml(name), escapeHtml(benutzername));
				user.addUser(newUser);
				HttpSession session = request.getSession();
				session.setAttribute("benutzername", benutzername);
				response.sendRedirect("hauptseite");
				user.complete();
				user.close();
			} else {
				loginStatus = true;
				doGet(request, response);
			}
		}
	}



}
