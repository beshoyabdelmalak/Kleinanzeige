package de.unidue.inf.is;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.stores.AnzeigeStore;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

/**
 * Servlet implementation class anzeigeEditierenServlet
 */
@WebServlet("/anzeigeEditierenServlet")
public class AnzeigeEditierenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String benutzername;
	private int id;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		try{
			benutzername = (String) session.getAttribute("username");
			id = Integer.parseInt(request.getParameter("id"));
			AnzeigeStore anzeigeStore = new AnzeigeStore();
			Anzeige anzeige = anzeigeStore.getAnzeige(id);
			request.setAttribute("valueOfTitel", anzeige.getTitel());
			request.setAttribute("valueOfText", anzeige.getText());
			request.setAttribute("valueOfPreis", anzeige.getPreis());
			ArrayList<String> kategorien = new ArrayList<>();
			kategorien = anzeigeStore.getKategorien(id);
			anzeigeStore.complete();
			anzeigeStore.close();
			for (int i = 1; i < 5; i++)
				request.setAttribute("valueOfchk" + i + "", "");
			for (String d : kategorien) {
				switch (d) {
				case "Digitale Waren":
					request.setAttribute("valueOfchk1", "checked");
					break;
				case "Haus & Garten":
					request.setAttribute("valueOfchk2", "checked");
					break;
				case "Mode & Kosmetik":
					request.setAttribute("valueOfchk3", "checked");
					break;
				case "Multimedia & Elektronik":
					request.setAttribute("valueOfchk4", "checked");
					break;
				default:
					System.out.println("etwas ist schief gelaufen");
				}
			}
			request.getRequestDispatcher("/anzeigeEditieren.ftl").forward(request, response);
		} catch(NullPointerException e) {
			request.setAttribute("message", "Sie haben sich nicht angemeldet, bitte melden Sie Sich bevor Sie in die Hauptseite kommen");
			request.setAttribute("hauptseite", "");
			request.setAttribute("melde", "anmelde");
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			id = Integer.parseInt(request.getParameter("id"));
			String titel = escapeHtml(request.getParameter("Titel"));
			String text = escapeHtml(request.getParameter("Text"));
			float preis = Float.valueOf(request.getParameter("Preis"));
			String[] kategorien = request.getParameterValues("chk[]");
			if (titel.length() <= 100 && !titel.isEmpty() && !text.isEmpty() && preis >= 0 && kategorien != null
					&& kategorien.length > 0 && preis < 1000) {
				Anzeige anzeige = new Anzeige(id, titel, text, preis);
				AnzeigeStore anzeigeStore = new AnzeigeStore();
				anzeigeStore.updateAnzeige(anzeige);
				anzeigeStore.deleteFromHatKategorie(id);
				for (String k : kategorien) {
					anzeigeStore.insertIntoHatKategorie(id, k);
				}
				anzeigeStore.complete();
				anzeigeStore.close();
				response.sendRedirect("hauptseite");
			}else {

				request.setAttribute("message",
						"Fehler aufgetreten!! möglicher Fehler ist dass Sie gar keine oder mehr als 100 Zeichen als Titel"
								+ ", negativen Preis eingegeben"
								+ ", leere Felder gelassen "
								+ ",oder gar keine Kategorien für Ihre Anzeige ausgewählt haben");
				request.setAttribute("hauptseite", "hauptseite");
				request.setAttribute("melde", "");
				request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
			}
		}catch (NumberFormatException e) {
			request.setAttribute("message",
					"Sie haben entweder eine Folge von Zeichen als Preis oder gar keinen Preis eingegeben !!");
			request.setAttribute("hauptseite", "hauptseite");
			request.setAttribute("melde", "");
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}

	}

}
