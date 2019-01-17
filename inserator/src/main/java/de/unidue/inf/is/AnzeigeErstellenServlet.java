package de.unidue.inf.is;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.*;
import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.stores.AnzeigeStore;



/**
 * Einfaches Beispiel, das die Vewendung der Template-Engine zeigt.
 */
public final class AnzeigeErstellenServlet extends HttpServlet {
	private String benutzername;

    private static final long serialVersionUID = 1L;



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Put the user list in request and let freemarker paint it.
		HttpSession session = request.getSession(false);
		if (session.getAttribute("username") != null) {
			benutzername = (String) session.getAttribute("username");
			request.getRequestDispatcher("/anzeigeErstellen.ftl").forward(request, response);
		}else {
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                    IOException {
    	

    	
    	//TODO den Teil von zeile 54 bis 67 hab ich noch nicht probiert
    	String titel = request.getParameter("Titel");
    	String text = request.getParameter("Text");
    	float preis = Float.valueOf(request.getParameter("Preis")) ;
    	String[] kategorien = request.getParameterValues("chk[]");
	
    	Anzeige anzeige = new Anzeige(titel,text,preis, benutzername, "aktiv");
    	AnzeigeStore anzeigeStore = new AnzeigeStore();
    	anzeigeStore.addAnzeige(anzeige);
    	int result = anzeigeStore.idOfTheLastInsertedValue("select max(a.id) from dbp64.anzeige a ");
    	for(String k: kategorien) {
    		anzeigeStore.insertIntoHatKategorie(result, k);
    	}
    	anzeigeStore.complete();
    	anzeigeStore.close();
 	
    

        doGet(request, response);
    }
}
