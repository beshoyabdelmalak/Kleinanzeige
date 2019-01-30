package de.unidue.inf.is;

import java.io.IOException; 
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.stores.AnzeigeStore;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;



public final class AnzeigeErstellenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		try {
			String benutzername = (String) session.getAttribute("benutzername");
			request.getRequestDispatcher("/anzeigeErstellen.ftl").forward(request, response);
		} catch (NullPointerException e) {
			request.setAttribute("message",
					"Sie haben sich nicht angemeldet, bitte melden Sie Sich bevor Sie eine Anzeige erstellen");
			request.setAttribute("hauptseite", "");
			request.setAttribute("melde", "anmelde");
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			String benutzername = (String) session.getAttribute("benutzername");
			//Werte vom user nehmen
			String titel = escapeHtml(request.getParameter("Titel"));
			String text = escapeHtml(request.getParameter("Text"));
			float preis = Float.valueOf(request.getParameter("Preis"));
			String[] kategorien = request.getParameterValues("chk[]");
			if (titel.length() <= 100 && !titel.isEmpty() && !text.isEmpty() && preis >= 0 && kategorien != null
					&& kategorien.length > 0 && preis < 1000) {
				//wenn die werte bestimmte Kriterien entspricht, werden in der Datenbank eingetragen
				Anzeige anzeige = new Anzeige(titel, text, preis, benutzername, "aktiv");
				AnzeigeStore anzeigeStore = new AnzeigeStore();
				anzeigeStore.addAnzeige(anzeige);
				int result = anzeigeStore.idOfTheLastInsertedValue("select max(a.id) from dbp64.anzeige a ");
				for (String k : kategorien) {
					//die vom Benutzer ausgewählte Kategorien eintragen
					anzeigeStore.insertIntoHatKategorie(result, k);
				}
				anzeigeStore.complete();
				anzeigeStore.close();
				response.sendRedirect("hauptseite");
			} else {

				request.setAttribute("message",
						"Fehler aufgetreten!! möglicher Fehler ist dass Sie gar keine oder mehr als 100 Zeichen als Titel"
								+ ", negativen Preis eingegeben"
								+ ", leere Felder gelassen "
								+ ",oder gar keine Kategorien für Ihre Anzeige ausgewählt haben");
				request.setAttribute("hauptseite", "hauptseite");
				request.setAttribute("melde", "");
				request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
			}

		} catch (NumberFormatException e) {
			request.setAttribute("message",
					"Sie haben entweder eine Folge von Zeichen als Preis oder gar keinen Preis eingegeben !!");
			request.setAttribute("hauptseite", "hauptseite");
			request.setAttribute("melde", "");
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}
	}
}
