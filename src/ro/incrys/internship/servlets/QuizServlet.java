package ro.incrys.internship.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.TestDBManager;
import ro.incrys.internship.entities.Candidate;
import ro.incrys.internship.entities.Quiz;
import services.QuizService;

/**
 * Servlet implementation class QuizServlet
 *  directs the candidate to take the quiz
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Quiz q;
		QuizService quizService = new QuizService();
		int quizId = Integer.parseInt(request.getParameter("id"));

		Candidate user = (Candidate) request.getSession().getAttribute("user");
		int testId = TestDBManager.findTestIdOfQuizId(quizId, user.getId());

		try {

			TestDBManager.updateStartTest(testId,
					new Timestamp(new Date().getTime()));
			HttpSession session = request.getSession();
			session.setAttribute("testId", testId);

			q = quizService.getEntityById(quizId);
			
			request.setAttribute("quizObject", q);

			request.setAttribute("questions", q.getQuestions());
			request.getRequestDispatcher("protected/quiz.jsp").forward(request,
					response);
		} catch (Exception e) {
			Logger.getLogger(QuizServlet.class).debug(e.getMessage());
			e.printStackTrace();
			response.sendRedirect("login.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
