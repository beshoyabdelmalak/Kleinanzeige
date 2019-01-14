package de.unidue.inf.is;

import java.awt.List; 
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private Anzeige anzeige;
	private boolean hilfsvar = true;
	private boolean hilfsvar1 = true;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(hilfsvar) {
			if(hilfsvar1) {
				AnzeigeStore anzeigeStore = new AnzeigeStore();
				id = Integer.parseInt(request.getParameter("id"));
			    anzeige = anzeigeStore.getAnzeige(id);
				erstller = anzeige.getErsteller();
				
				ArrayList<Anzeige> anzeigeZuAnzeige = new ArrayList<>();
				anzeigeZuAnzeige.add(anzeige);
				request.setAttribute("anzeigeDeteils", anzeigeZuAnzeige);
				request.setAttribute("kaeufer", LoginServlet.getAngemeldeterBenutzer());
				request.setAttribute("status", anzeige.getStatus());
				
				kommentaren = anzeigeStore.getAllKommentaren(id);
				request.setAttribute("kommentaren", kommentaren);
				anzeigeStore.complete();
				anzeigeStore.close();
				request.getRequestDispatcher("/anzeigeDetails.ftl").forward(request, response);	
			}else {
				hilfsvar1 = true;
				response.sendRedirect("hauptseite");
			}
		}else {
			hilfsvar = true;
			System.out.println("du hast recht");
			response.sendRedirect("anzeigeEditieren?id="+ id);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AnzeigeStore anzeigeStore = new AnzeigeStore();
		int id = Integer.parseInt(request.getParameter("id"));
		String kommentarfield = request.getParameter("kommentarfield");
		if(null != kommentarfield && !kommentarfield.isEmpty()) {
			anzeigeStore.addKommentar(kommentarfield);
			int result = anzeigeStore.idOfTheLastInsertedValue("select max(a.id) from dbp64.kommentar a ");
			anzeigeStore.insertIntoHatKommentar(result , LoginServlet.getAngemeldeterBenutzer(), id);
		}else {
			if(!erstller.equals(LoginServlet.getAngemeldeterBenutzer())) {
				anzeigeStore.insertIntoKauft(LoginServlet.getAngemeldeterBenutzer(), id);

		}else {
			if(request.getParameter("vomVerkäufer").equals("Löschen")) {
				hilfsvar1 = false;
				anzeigeStore.deleteAnzeigeWithId(id);
			}else{
				hilfsvar = false;	// hilfvar hilft beim redirct to editieren  			
			}
		}
		}
		
		
		
		
		anzeigeStore.complete();
		anzeigeStore.close();
		doGet(request, response);
	}

}
