package ro.incrys.internship.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.TestDBManager;
import ro.incrys.internship.entities.Question;
import ro.incrys.internship.entities.Quiz;
import services.QuizService;
import services.TestService;

/**
 * Servlet implementation class ProcessQuizServlet computes the score for a
 * simple of multiple choice quiz
 */
@WebServlet("/ProcessQuizServlet")
public class ProcessQuizServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProcessQuizServlet() {
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

		QuizService quizService = new QuizService();
		long finalTime = new Date().getTime();
		long duration = 0;
		HttpSession session = request.getSession();
		int testId = 0;
		int quizId = 0;
		Quiz quiz = null;
		double penalty = 0.0;
		int noCorrectQuestions = 0;
		int countAnswers = 0;
		int total = 0;
		int timeLimit = 0;
		try {

			testId = (int) session.getAttribute("testId");

			quizId = Integer.parseInt(request.getParameter("quizId"));
			quiz = quizService.getEntityById(quizId);

			timeLimit = quiz.getTimeLimit();

			total = quiz.getQuestions().size();

			double increment = 100 / total;
			int userAnswerId = 0;
			Collection<String> userAnswers = null;

			for (Question question : quiz.getQuestions()) {

				if (request.getParameter("RId" + question.getId()) != null) {

					userAnswerId = Integer.parseInt(request.getParameter("RId"
							+ question.getId()));
					try {
						TestDBManager.createCandidateAnswer(testId,
								question.getId(), userAnswerId);

						boolean isCorrect = TestDBManager
								.checkCorrectAnswer(userAnswerId);

						if (isCorrect) {

							noCorrectQuestions++;
						}

					} catch (NumberFormatException | ClassNotFoundException
							| SQLException e) {
						Logger.getLogger(ProcessQuizServlet.class).debug(
								e.getMessage());
						e.printStackTrace();
					}

				} else if (request.getParameter("CId" + question.getId()) != null) {
					int noCorrectAnswers = TestDBManager
							.getMultipleCorrectAnswers(question.getId());

					userAnswers = new ArrayList<String>(Arrays.asList(request
							.getParameterValues("CId" + question.getId())));

					for (String ua : userAnswers) { // multiple correct answers

						int uaAnswerId = Integer.parseInt(ua);
						try {
							TestDBManager.createCandidateAnswer(testId,
									question.getId(), uaAnswerId);

						} catch (ClassNotFoundException | SQLException e) {
							Logger.getLogger(ProcessQuizServlet.class).debug(
									e.getMessage());
							e.printStackTrace();
						}
						boolean isCorrect = TestDBManager
								.checkCorrectAnswer(uaAnswerId);
						if (isCorrect) {
							countAnswers++;

						} else {
							penalty = penalty - increment / 2;
						}
					}
					if (noCorrectAnswers == countAnswers) {
						noCorrectQuestions++;

					}
					countAnswers = 0;

				}

			}
			double finalScore = (noCorrectQuestions * increment) + penalty;
			double score = (finalScore > 0) ? finalScore : 0;

			try {
				TestDBManager.updateTest(testId, new Timestamp(finalTime),
						score);
				duration = TestDBManager.getTestInformation(testId);

			} catch (ClassNotFoundException e) {
				Logger.getLogger(ProcessQuizServlet.class)
						.debug(e.getMessage());
				e.printStackTrace();
			}

			if (timeLimit != 0) {
				if (duration > timeLimit + 1) {

					TestService ts = new TestService();
					ts.deleteEntity(testId);
					response.sendRedirect("errorPage.jsp");
					return;
				}
			}
			request.setAttribute("score", score);
			request.setAttribute("timeMessage", "Test duration: " + duration
					+ " minutes");

			// remove the testId at the end of the test
			session.removeAttribute("testId");

			request.getRequestDispatcher("protected/results.jsp").forward(
					request, response);

		} catch (Exception e) {
			Logger.getLogger(ProcessQuizServlet.class).debug(e.getMessage());
			e.printStackTrace();
			response.sendRedirect("../../DisplayQuizesServlet");
		}
	}
}
