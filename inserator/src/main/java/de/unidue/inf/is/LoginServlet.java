package de.unidue.inf.is;

import java.io.IOException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ntp.TimeStamp;

import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.AnzeigeStore;
import de.unidue.inf.is.stores.UserStore;



/**
 * Einfaches Beispiel, das die Verwendung des {@link UserStore}s zeigt.
 */
public final class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<String> userList = new ArrayList<>();
    private boolean loginStatus = false;
    private boolean hilfsvar = true;
    private static String angemeldeterBenutzer;
    public static String getAngemeldeterBenutzer() {
		return angemeldeterBenutzer;
	}
	public static void setAngemeldeterBenutzer(String angemeldeterBenutzer) {
		LoginServlet.angemeldeterBenutzer = angemeldeterBenutzer;
	}
	// Just prepare static data to display on screen
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		request.setAttribute("navtype", "general");
    		if(loginStatus) {    			
    			loginStatus = false;
    			hilfsvar = true;
    			response.sendRedirect("hauptseite");  //request.getRequestDispatcher("/anzeigeErstellen.ftl").forward(request, response);   			
    		}else {
    			if(!hilfsvar && !loginStatus){
    				hilfsvar = true;
    				request.setAttribute("navtype", "false");
    			}
    			request.getRequestDispatcher("/login.ftl").forward(request, response);
    		}
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                    IOException {
    	String benutzername = request.getParameter("Benutzername");
    	UserStore user = new UserStore();
    	userList = user.getUserNames();
    	for(String u: userList ) {
    		if(benutzername.equals(u)){
    			loginStatus= true;
    			setAngemeldeterBenutzer(u);
    		}else {
    			hilfsvar = false;
    			
    		}
    	}
       

        doGet(request, response);
    }

}
