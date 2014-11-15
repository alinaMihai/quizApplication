package ro.incrys.internship.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.TestDBManager;
import ro.incrys.internship.DatabaseManager.UserManager;
import services.CandidateService;

/**
 * Servlet implementation class UserServlet
 * creates a candidate
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("cname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		ArrayList<String> quizes = null;
		int candidateId = UserManager.createUser(name, username, password);

		if (request.getParameter("quiz") != null) {

			quizes = new ArrayList<String>(Arrays.asList(request
					.getParameterValues("quiz")));
			for (String quizId : quizes) {
				int qId = Integer.parseInt(quizId);
				try {
					TestDBManager.createTest(candidateId, qId);
				} catch (ClassNotFoundException | SQLException e) {
					Logger.getLogger(UserServlet.class).debug(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		CandidateService cs = new CandidateService();
		HttpSession session = request.getSession();
		session.setAttribute("candidates", cs.getEntityList());
		session.setAttribute("noticeM", "User successfully created");

		response.sendRedirect("protected/admin/createUser.jsp");

	}
}
