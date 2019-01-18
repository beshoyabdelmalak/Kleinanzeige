package de.unidue.inf.is;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.stores.AnzeigeStore;

/**
 * Servlet implementation class HauptseiteServlet
 */
@WebServlet("/UserDetailsServlet")
public class HauptseiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HauptseiteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			session.getAttribute("username");
			AnzeigeStore anzeigeStore = new AnzeigeStore(); 
			ArrayList<Anzeige> array = new ArrayList<>();
			array = anzeigeStore.getAllAnzeige();
			anzeigeStore.complete();
			anzeigeStore.close();
			request.setAttribute("result", array);
			request.getRequestDispatcher("/hauptseite.ftl").forward(request, response);
		}catch(NullPointerException e ) {
			request.getRequestDispatcher("/ErrorAnmeldung.ftl").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
