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
import de.unidue.inf.is.domain.Kommentar;

import de.unidue.inf.is.stores.AnzeigeStore;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

/**
 * Servlet implementation class AnzeigeDetaeisServlet
 */
@WebServlet("/AnzeigeDetaeisServlet")
public class AnzeigeDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Kommentar> kommentaren = new ArrayList<>();


	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {

			String benutzername = (String) session.getAttribute("benutzername");
			AnzeigeStore anzeigeStore = new AnzeigeStore();
			int id = Integer.parseInt(request.getParameter("id"));
			Anzeige anzeige = anzeigeStore.getAnzeige(id);

			request.setAttribute("anzeige", anzeige);
			//die VAriablen kaeufer und status wurden benötigt um die simultaion der Tasten editeiren, löschen und kaufen zu schaffen
			request.setAttribute("kaeufer", benutzername);
			request.setAttribute("status", anzeige.getStatus());
			kommentaren = anzeigeStore.getAllKommentaren(id);
			request.setAttribute("kommentaren", kommentaren);

			if (anzeige.getStatus().contentEquals("verkauft"))
				request.setAttribute("gekauftvon", anzeigeStore.getKäufer(id));
			// wenn die Anzeige gekauft wurde und einen Zugriff darauf gehabt wurde wird der
			// Käufer auch angezeigt
			else
				request.setAttribute("gekauftvon", "");
			// gekauft von wird nur angezeigt, wenn die Anzeige schon verkauft wurde, ob sie
			// verkauft oder nicht, wurde das in FTL
			// Datei behandelt durch die FTL_variable status in der zeile 60

			anzeigeStore.complete();
			anzeigeStore.close();

			request.getRequestDispatcher("/anzeigeDetails.ftl").forward(request, response);
		} catch (NullPointerException e) {
			// Fehler behandlung, wenn ein Fremder einen Zugriff auf eine Seite, auf die er
			// keinen zugriff hat
			request.setAttribute("message",
					"Sie haben sich nicht angemeldet, bitte melden Sie Sich bevor Sie in die Anzeigedetailsseite kommen");
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
		HttpSession session = request.getSession(false);
		String benutzername = (String) session.getAttribute("benutzername");
		// wem welche Tasten angezeigt werden müßen, wurde das in der entsprechnden
		// FTL-Datei behandelt mit hilfe von Variable Status
		// in der zeile 60 und die variable kaeufer in der zeile 59
		int id = Integer.parseInt(request.getParameter("id"));
		String kommentarfield = escapeHtml(request.getParameter("kommentarfield"));
		String action = request.getParameter("action");
		// action ist eine variable kommt per url von html, definiert welche tast
		// gedrückt wurde
		if (null != kommentarfield && !kommentarfield.isEmpty() && action.equals("kommentieren")) {
			AnzeigeStore anzeigeStore = new AnzeigeStore();
			anzeigeStore.addKommentar(kommentarfield);
			int result = anzeigeStore.idOfTheLastInsertedValue("select max(a.id) from dbp64.kommentar a ");
			anzeigeStore.insertIntoHatKommentar(result, benutzername, id);
			anzeigeStore.complete();
			anzeigeStore.close();
			doGet(request, response);
		} else {
			if (action.equals("kaufen")) {
				AnzeigeStore anzeigeStore = new AnzeigeStore();
				if (anzeigeStore.getAnzeige(id).getStatus().equals("aktiv   ")) {
					// Achtung lösche das leere String nach aktiv nicht, das ist teil der Implemetierung
					anzeigeStore.insertIntoKauft(benutzername, id);
					anzeigeStore.complete();
					anzeigeStore.close();
					doGet(request, response);
				} else {
					anzeigeStore.complete();
					anzeigeStore.close();
					// Fehler behndlung wenn 2 gleichzeitig die selbe Anzeige kaufen gedrückt haben
					request.setAttribute("message", "die Anzeige ist leider schon verkauft");
					request.setAttribute("hauptseite", "hauptseite");
					request.setAttribute("melde", "");
					request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
				}
			} else {
				if (action.equals("löschen")) {
					AnzeigeStore anzeigeStore = new AnzeigeStore();
					ArrayList<Integer> kommentarIDs = new ArrayList<>();
					kommentarIDs = anzeigeStore.getkommentarIDsEinerAnzeige(id);
					for (int k : kommentarIDs) {
						anzeigeStore.deleteKommentarWithId(k);
						//in dieser schleife wird alle Kommentaren zu der gelöschten Anzeige eben gelöscht
					}
					anzeigeStore.deleteAnzeigeWithId(id);
					anzeigeStore.complete();
					anzeigeStore.close();
					response.sendRedirect("hauptseite");
				} else {
					if (action.equals("editieren")) {
						response.sendRedirect("anzeigeEditieren?id=" + id);
					} else {
						doGet(request, response);
					}
				}
			}

		}

	}

}
