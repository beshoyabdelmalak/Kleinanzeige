package de.unidue.inf.is;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.Nachricht;
import de.unidue.inf.is.stores.NachrichtStore;

/**
 * Servlet implementation class NachrichtServlet
 */
@WebServlet("/NachrichtServlet")
public class NachrichtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NachrichtServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doget dient dazu, die nachrichten zwichen der angemeldeter und der andere benutzer zu zeigen
		HttpSession session = request.getSession(false);
    	try{
    		//der angemldeter benutzer (absender)
    		String signedinUser = (String) session.getAttribute("benutzername"); 
    		//der Benutzer, zu dem die profile gehoert (empfaenger)
		    String userprofile = request.getParameter("user");
		    NachrichtStore nachrichtStore = new NachrichtStore();
		    ArrayList<Nachricht> nachrichten = nachrichtStore.getNachricht(signedinUser, userprofile);
		    nachrichtStore.complete();
		    nachrichtStore.close();
		    request.setAttribute("nachrichten", nachrichten);
		    request.setAttribute("user", userprofile);
		    request.getRequestDispatcher("/nachricht.ftl").forward(request, response);
    	}catch(Exception e){
    		request.setAttribute("message", "Sie haben sich nicht angemeldet, bitte melden Sie Sich bevor Sie in die Hauptseite kommen");
			request.setAttribute("hauptseite", "");
			request.setAttribute("melde", "anmelde");
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// dopost dient dazu, neue nachricht in der Db hinzufuegen
		String message = request.getParameter("message");
		if (!message.isEmpty()) {
			HttpSession session = request.getSession(false);
			String absender = (String) session.getAttribute("benutzername");
			String empfaenger = request.getParameter("user");
			Nachricht nachricht = new Nachricht(message, absender, empfaenger);
			NachrichtStore nachrichtStore = new NachrichtStore();
			nachrichtStore.addNachricht(nachricht);
			nachrichtStore.complete();
			nachrichtStore.close();
			doGet(request, response);
		}else {
			doGet(request, response);
		}
	}

}
