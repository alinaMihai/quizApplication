package ro.incrys.internship.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.UserManager;
import ro.incrys.internship.entities.Candidate;

/**
 * Servlet implementation class LoginServlet logs in and out a user
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// sign out
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username"), password = request
				.getParameter("password");

		if (username != null && password != null) {
			try {

				Candidate user = UserManager.getUser(username, password);
				if (user != null) {

					HttpSession session = request.getSession();
					session.setAttribute("user", user);

					response.sendRedirect("DisplayQuizesServlet");
				} else {

					request.setAttribute("errorMessage",
							"Wrong user name or password. Please try again");
					request.setAttribute("username", username);
					request.getRequestDispatcher("login.jsp").forward(request,
							response);
				}
			} catch (ClassNotFoundException e) {
				Logger.getLogger(LoginServlet.class).debug(e.getMessage());
				e.printStackTrace();
			}

		}
	}

}
