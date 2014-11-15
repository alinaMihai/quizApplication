package ro.incrys.internship.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.TestDBManager;
import ro.incrys.internship.entities.Quiz;
import services.QuizService;

/**
 * Servlet implementation class AddQuizToCandidate
 */
@WebServlet("/AddQuizToCandidateServlet")
public class AddQuizToCandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddQuizToCandidateServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int candidateId = Integer.parseInt((String) request.getParameter("id"));
		QuizService quizService = new QuizService();
		Collection<Quiz> quizes = quizService
				.getNewQuizesForCandidate(candidateId);
		request.setAttribute("candidateId", candidateId);
		request.setAttribute("newQuizes", quizes);
		request.getRequestDispatcher("protected/admin/addQuizToCandidate.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ArrayList<String> quizes = null;
		HttpSession session = request.getSession();
		int candidateId = Integer.parseInt(request.getParameter("candidateId"));

		if (request.getParameter("quiz") != null) {

			quizes = new ArrayList<String>(Arrays.asList(request
					.getParameterValues("quiz")));

			for (String quizId : quizes) {
				int qId = Integer.parseInt(quizId);
				try {

					TestDBManager.createTest(candidateId, qId);

				} catch (ClassNotFoundException | SQLException e) {
					session.setAttribute("errorM",
							"Quizes could not be assigned");

					request.getRequestDispatcher(
							"protected/admin/addQuizToCandidate.jsp").forward(
							request, response);
					e.printStackTrace();
					Logger.getLogger(AddQuizToCandidateServlet.class).debug(
							e.getMessage());
					return;
				}
			}
		}
		session.setAttribute("notifyMessage", "Operation successful");
		response.sendRedirect("protected/admin/viewCandidates.jsp");
	}

}
