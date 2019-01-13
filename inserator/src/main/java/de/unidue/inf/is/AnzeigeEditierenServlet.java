package de.unidue.inf.is;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.stores.AnzeigeStore;

/**
 * Servlet implementation class anzeigeEditierenServlet
 */
@WebServlet("/anzeigeEditierenServlet")
public class AnzeigeEditierenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/anzeigeEditieren.ftl").forward(request, response);	
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));		
		System.out.println(""+id);
    	String titel = request.getParameter("Titel");
    	String text = request.getParameter("Text");
    	float preis = Float.valueOf(request.getParameter("Preis")) ;
    	String[] kategorien = request.getParameterValues("chk[]");
	
    	Anzeige anzeige = new Anzeige(id,titel,text,preis);
    	AnzeigeStore anzeigeStore = new AnzeigeStore();
    	anzeigeStore.updateAnzeige(anzeige);
    	anzeigeStore.deleteFromHatKategorie(id);
    	for(String k: kategorien) {
    		anzeigeStore.insertIntoHatKategorie(id, k);
    	}
    	anzeigeStore.complete();
    	anzeigeStore.close();
		doGet(request, response);
	}

}
