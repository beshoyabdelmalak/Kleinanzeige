package de.unidue.inf.is;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.UserStore;



/**
 * Einfaches Beispiel, das die Verwendung des {@link UserStore}s zeigt.
 */
public final class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<User> userList = new ArrayList<>();
    private boolean loginStatus = false;
    private boolean hilfsvar = true;
    private static String ersteller;
    // Just prepare static data to display on screen
    static {
        userList.add(new User("Bill Gates", "BillGates"));
        userList.add(new User("Steve Jobs", "SteveJobs"));
        userList.add(new User("Larry Page", "LarryPage"));
        userList.add(new User("Sergey Brin", "SergeyBrin"));
        userList.add(new User("Larry Ellison", "LarryEllison"));
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		request.setAttribute("navtype", "general");
    		if(loginStatus) {
    			System.out.println("hilfsvar "+hilfsvar+" hilfsvar "+loginStatus+ " im 1.if"); 
    			loginStatus = false;
    			hilfsvar = true;
    			request.getRequestDispatcher("/hauptseite.ftl").forward(request, response);   			
    		}else {
    			if(!hilfsvar && !loginStatus){
    				System.out.println("hilfsvar "+hilfsvar+" hilfsvar "+loginStatus +" im 2.if");
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
    	for(User u: userList ) {
    		if(benutzername.equals(u.getBenutzerName())) {
    			loginStatus= true;
    			ersteller = u.getname();    			
    		}else {
    			hilfsvar = false;
    		}
    		
    		
    		
    	}
       

        doGet(request, response);
    }

}
