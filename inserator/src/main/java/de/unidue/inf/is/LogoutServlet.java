package de.unidue.inf.is;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




/**
 * Einfaches Beispiel, das die Vewendung der Template-Engine zeigt.
 */
public final class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Put the user list in request and let freemarker paint it.
       HttpSession session = request.getSession(false);
       try{
    	   session.removeAttribute("username");
    	   session.invalidate();
    	   response.sendRedirect("login");
       }catch(Exception e) {
    	   request.setAttribute("message", "Sie haben sich nicht angemeldet, bitte melden Sie Sich bevor Sie in die Hauptseite kommen");
			request.setAttribute("hauptseite", "");
			request.setAttribute("melde", "anmelde");
    	   request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
       }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                    IOException {
        doGet(request, response);
    }
}
