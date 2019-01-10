package de.unidue.inf.is;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.*;
import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.stores.AnzeigeStore;



/**
 * Einfaches Beispiel, das die Vewendung der Template-Engine zeigt.
 */
public final class AnzeigeErstellenServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Put the user list in request and let freemarker paint it.

        request.getRequestDispatcher("/anzeigeErstellen.ftl").forward(request, response);
        System.out.println(request.getParameter("username"));
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                    IOException {
    	

    	
    	//TODO den Teil von zeile 54 bis 67 hab ich noch nicht probiert
    	String titel = request.getParameter("Titel");
    	String text = request.getParameter("Text");
    	float preis = Float.valueOf(request.getParameter("Preis")) ;
    	String[] kategorien = request.getParameterValues("chk[]");
	
    	Anzeige anzeige = new Anzeige(titel,text,preis, "aktiv", LoginServlet.getErsteller());
    	AnzeigeStore anzeigeStore = new AnzeigeStore();
    	anzeigeStore.addAnzeige(anzeige);
    	int id = anzeigeStore.getIDofInsertedQ("insert into Anzeige (titel, text, preis, ersteller, status) values ('"+titel+"','"+text+"',"+",'"+preis+"',"+",'aktiv','"+ LoginServlet.getErsteller()+"')");        
    	for(String k: kategorien) {
    		anzeigeStore.insertIntoHatKategorie(id, k);
    	}
    	anzeigeStore.complete();
    	anzeigeStore.close();

//    	System.out.println(titel);
//    	System.out.println(text);
//    	System.out.println(preis);
//    	System.out.println(LoginServlet.getErsteller());
//    	for(String s: kategorien) {
//    		System.out.println(s);
//    	}
//    	String s = "insert into Anzeige (titel, text, preis, ersteller, status) values ('"+titel+"','"+text+"',"+",'"+preis+"',"+",'aktiv','"+ LoginServlet.getErsteller()+"')";
//    	System.out.println(s);
//    	
    	
    

        doGet(request, response);
    }
}
