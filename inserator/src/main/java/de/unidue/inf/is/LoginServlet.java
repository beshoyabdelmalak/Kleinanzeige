package de.unidue.inf.is;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import de.unidue.inf.is.stores.UserStore;



/**
 * Einfaches Beispiel, das die Verwendung des {@link UserStore}s zeigt.
 */
public final class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<String> userList = new ArrayList<>();
    private boolean loginStatus = false;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		request.setAttribute("navtype", "general");
    		if(loginStatus) {
				request.setAttribute("navtype", "false");
				loginStatus = false;
    		}
    		request.getRequestDispatcher("/login.ftl").forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                    IOException {
    	String benutzername = request.getParameter("Benutzername");
    	UserStore user = new UserStore();
    	boolean hilfsvar = true;
    	userList = user.getUserNames();
    	for(String u: userList ) {
    		if(benutzername.equals(u)){
    			HttpSession session = request.getSession();
    			session.setAttribute("username", benutzername);
    			response.sendRedirect("hauptseite");
    			hilfsvar = false;
    		}
    	}
    	if(hilfsvar) {
    		loginStatus = true;
			doGet(request, response);
		}
    }

}
