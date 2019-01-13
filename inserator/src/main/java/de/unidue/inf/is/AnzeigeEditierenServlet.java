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
		response.getWriter().append("anzeigeEditieren").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	//TODO den Teil von zeile 54 bis 67 hab ich noch nicht probiert
		//String y = request.getParameter("id");		
		//System.out.println(y);
//    	String titel = request.getParameter("Titel");
//    	String text = request.getParameter("Text");
//    	float preis = Float.valueOf(request.getParameter("Preis")) ;
//    	String[] kategorien = request.getParameterValues("chk[]");
//	
//    	Anzeige anzeige = new Anzeige(titel,text,preis, LoginServlet.getAngemeldeterBenutzer(), "aktiv");
//    	AnzeigeStore anzeigeStore = new AnzeigeStore();
//    	anzeigeStore.addAnzeige(anzeige);
//    	int result = anzeigeStore.idOfTheLastInsertedValue("select max(a.id) from dbp64.anzeige a ");
//    	System.out.println(result);
//    	for(String k: kategorien) {
//    		anzeigeStore.insertIntoHatKategorie(result, k);
//    	}
//    	anzeigeStore.complete();
//    	anzeigeStore.close();
		doGet(request, response);
	}

}
