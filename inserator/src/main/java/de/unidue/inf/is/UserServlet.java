package de.unidue.inf.is;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.AnzeigeStore;
import de.unidue.inf.is.stores.UserStore;



/**
 * Einfaches Beispiel, das die Verwendung des {@link UserStore}s zeigt.
 */
public final class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
		if (session.getAttribute("username") != null) {
		    String username = request.getParameter("username");
		    
		    //get the user information
		    UserStore userStore = new UserStore();
		    User user = userStore.getUser(username);
		    userStore.complete();
		    userStore.close();
		    request.setAttribute("username", user.getBenutzerName());
		    request.setAttribute("name", user.getName());
		    request.setAttribute("items", user.getGekauft());
		    request.setAttribute("date", user.getEintrittsDatum());
		    
		    //get the offered ads
		    AnzeigeStore anzeigeStore = new AnzeigeStore();
		    ArrayList<Anzeige> anzeige = anzeigeStore.getOffersByUsername(username);
		    request.setAttribute("result", anzeige);
		    
		    //get the purchased items
		    ArrayList<Anzeige> purchased = anzeigeStore.getPurchasedOffers(username);
		    request.setAttribute("purchased", purchased);
		    request.getRequestDispatcher("/user.ftl").forward(request, response);
		}else {
			request.setAttribute("message", "fehler ist aufgetreten");
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}
    }

}
