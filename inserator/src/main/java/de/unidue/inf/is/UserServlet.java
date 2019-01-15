package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.UserStore;



/**
 * Einfaches Beispiel, das die Verwendung des {@link UserStore}s zeigt.
 */
public final class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String s = request.getParameter("username");
        UserStore userStore = new UserStore();
        User user = userStore.getUser(s);
        request.setAttribute("username", user.getBenutzerName());
        request.setAttribute("name", user.getName());
        request.setAttribute("items", user.getGekauft());
        request.setAttribute("date", user.getEintrittsDatum());
        request.getRequestDispatcher("/user.ftl").forward(request, response);

    }

}
