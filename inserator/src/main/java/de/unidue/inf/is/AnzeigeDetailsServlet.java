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

/**
 * Servlet implementation class AnzeigeDetaeisServlet
 */
@WebServlet("/AnzeigeDetaeisServlet")
public class AnzeigeDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String erstller;
	private String benutzername;
	private Anzeige anzeige;
	private int id;
	private ArrayList<Kommentar> kommentaren = new ArrayList<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnzeigeDetailsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			benutzername = (String) session.getAttribute("username");
			AnzeigeStore anzeigeStore = new AnzeigeStore();
			id = Integer.parseInt(request.getParameter("id"));
			anzeige = anzeigeStore.getAnzeige(id);
			erstller = anzeige.getErsteller();

			ArrayList<Anzeige> anzeigeZuAnzeige = new ArrayList<>();
			anzeigeZuAnzeige.add(anzeige);
			request.setAttribute("anzeigeDeteils", anzeigeZuAnzeige);
			request.setAttribute("kaeufer", benutzername);
			request.setAttribute("status", anzeige.getStatus());

			kommentaren = anzeigeStore.getAllKommentaren(id);
			request.setAttribute("kommentaren", kommentaren);
			anzeigeStore.complete();
			anzeigeStore.close();
			request.getRequestDispatcher("/anzeigeDetails.ftl").forward(request, response);
		} catch(NullPointerException e) {
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AnzeigeStore anzeigeStore = new AnzeigeStore();
		int id = Integer.parseInt(request.getParameter("id"));
		String kommentarfield = request.getParameter("kommentarfield");
		String action = request.getParameter("action");
		if (null != kommentarfield && !kommentarfield.isEmpty() && action.equals("kommentieren")) {
			anzeigeStore.addKommentar(kommentarfield);
			int result = anzeigeStore.idOfTheLastInsertedValue("select max(a.id) from dbp64.kommentar a ");
			anzeigeStore.insertIntoHatKommentar(result, benutzername, id);
			anzeigeStore.complete();
			anzeigeStore.close();
			doGet(request, response);
		} else {
			if (action.equals("kaufen")) {
				anzeigeStore.insertIntoKauft(benutzername, id);
				anzeigeStore.complete();
				anzeigeStore.close();
				response.sendRedirect("hauptseite");
			} else {
				if (action.equals("löschen")) {
					ArrayList<Integer> kommentarIDs = new ArrayList<>();
					kommentarIDs = anzeigeStore.getkommentarIDsEinerAnzeige(id);
					for(int k : kommentarIDs) {
						anzeigeStore.deleteKommentarWithId(k);
					}
					anzeigeStore.deleteAnzeigeWithId(id);
					anzeigeStore.complete();
					anzeigeStore.close();
					response.sendRedirect("hauptseite");
				} else {
					if (action.equals("editieren")) {
						anzeigeStore.complete();
						anzeigeStore.close();
						response.sendRedirect("anzeigeEditieren?id=" + id);

					} else {
						doGet(request, response);
					}
				}
			}

		}
	}

}
