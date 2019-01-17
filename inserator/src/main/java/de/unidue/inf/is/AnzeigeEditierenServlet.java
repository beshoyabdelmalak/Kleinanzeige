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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session.getAttribute("username") != null) {
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
			for(int i = 1 ; i <5; i++) request.setAttribute("valueOfchk"+i+"", "");
			for(String d : kategorien) {		         
			        switch(d){ 
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
//				if(d.equals("Digitale Waren")) request.setAttribute("valueOfchk1", "checked");else request.setAttribute("valueOfchk1", "");
//				if(d.equals("Haus & Garten")) request.setAttribute("valueOfchk2", "checked");else request.setAttribute("valueOfchk1", "");
//				if(d.equals("Mode & Kosmetik")) request.setAttribute("valueOfchk3", "checked");else request.setAttribute("valueOfchk1", "");
//				if(d.equals("Multimedia & Elektronik")) request.setAttribute("valueOfchk4", "checked");else request.setAttribute("valueOfchk1", "");
			}
			request.getRequestDispatcher("/anzeigeEditieren.ftl").forward(request, response);
		}else {
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}
			
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		id = Integer.parseInt(request.getParameter("id"));		
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
