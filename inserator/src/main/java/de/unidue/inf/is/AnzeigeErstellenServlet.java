package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.stores.AnzeigeStore;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

/**
 * Einfaches Beispiel, das die Vewendung der Template-Engine zeigt.
 */
public final class AnzeigeErstellenServlet extends HttpServlet {
	private String benutzername;

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Put the user list in request and let freemarker paint it.
		HttpSession session = request.getSession(false);
		try {
			benutzername = (String) session.getAttribute("username");
			request.getRequestDispatcher("/anzeigeErstellen.ftl").forward(request, response);
		} catch (NullPointerException e) {
			request.setAttribute("message",
					"Sie haben sich nicht angemeldet, bitte melden Sie Sich bevor Sie in die Hauptseite kommen");
			request.setAttribute("hauptseite", "");
			request.setAttribute("melde", "anmelde");
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO den Teil von zeile 54 bis 67 hab ich noch nicht probiert
		String titel = escapeHtml(request.getParameter("Titel"));
		String text = escapeHtml(request.getParameter("Text"));
		float preis = Float.valueOf(request.getParameter("Preis"));
		String[] kategorien = request.getParameterValues("chk[]");
		if (titel.length() <= 100 && preis >= 0 && kategorien.length >= 0) {
			Anzeige anzeige = new Anzeige(titel, text, preis, benutzername, "aktiv");
			AnzeigeStore anzeigeStore = new AnzeigeStore();
			anzeigeStore.addAnzeige(anzeige);
			int result = anzeigeStore.idOfTheLastInsertedValue("select max(a.id) from dbp64.anzeige a ");
			for (String k : kategorien) {
				anzeigeStore.insertIntoHatKategorie(result, k);
			}
			anzeigeStore.complete();
			anzeigeStore.close();

			doGet(request, response);
		} else {
			request.setAttribute("message",
					"Fehler aufgetreten, möglicher Fehler ist dass Sie gar keine oder mehr als 100 Zeichen\n"
							+ ", negativen Preis eingegeben\n  ,oder keine Kategorien ausgewählt haben");
			request.setAttribute("hauptseite", "hauptseite");
			request.setAttribute("melde", "");
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}
	}
}
