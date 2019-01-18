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
       if(session.getAttribute("username") != null)
    	   session.removeAttribute("username");
       response.sendRedirect("login");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                    IOException {
        doGet(request, response);
    }
}
