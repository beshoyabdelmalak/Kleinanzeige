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
import de.unidue.inf.is.stores.AnzeigeStore;

/**
 * Servlet implementation class AnzeigeDetaeisServlet
 */
@WebServlet("/AnzeigeDetaeisServlet")
public class AnzeigeDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String erstller;
	private Anzeige anzeige;
	private boolean status = true;
	private boolean hilfsvar = true;
    private int id;
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
		// TODO Auto-generated method stub
		if(hilfsvar) {
			if(status) {
				id = Integer.parseInt(request.getParameter("id"));
				AnzeigeStore anzeigeStore = new AnzeigeStore();
			    anzeige = anzeigeStore.getAnzeige(id);
				erstller = anzeige.getErsteller();
				
				ArrayList<Anzeige> anzeigeZuAnzeige = new ArrayList<>();
				anzeigeZuAnzeige.add(anzeige);
				request.setAttribute("anzeigeDeteils", anzeigeZuAnzeige);
				request.setAttribute("kaeufer", LoginServlet.getAngemeldeterBenutzer());
				request.setAttribute("status", anzeige.getStatus());
				anzeigeStore.complete();
				anzeigeStore.close();
				request.getRequestDispatcher("/anzeigeDetails.ftl").forward(request, response);
			}else {
				status = true;
				request.getRequestDispatcher("/ErrorAnzeigenotfound.ftl").forward(request, response);
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
		System.out.println("du bist in post von details new ");
		//LoginServlet loginServlet = new LoginServlet();
		
		int id = Integer.parseInt(request.getParameter("id"));
		if(!erstller.equals(LoginServlet.getAngemeldeterBenutzer())) {
			System.out.println("du hast kaufen gedrückt");
			System.out.println(anzeige.getStatus());
			if(anzeige.getStatus().equals("aktiv   ")) {
				AnzeigeStore anzeigeStore = new AnzeigeStore();
				anzeigeStore.insertIntoKauft(LoginServlet.getAngemeldeterBenutzer(), id);
				anzeigeStore.complete();
				anzeigeStore.close();
			}else {
				status = false;
			}
			
		}else {
			if(request.getParameter("vomVerkäufer").equals("Löschen")) {
				AnzeigeStore anzeigeStore = new AnzeigeStore();
				System.out.println("du hast löschen gedrückt");
				response.sendRedirect("hauptseite");
				anzeigeStore.deleteAnzeigeWithId(id);
				anzeigeStore.complete();
				anzeigeStore.close();
			}else{
				hilfsvar = false;
				System.out.println("du hast editieren gedrückt");
				
			}
		}
		doGet(request, response);
	}

}
