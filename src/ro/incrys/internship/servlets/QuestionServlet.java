package ro.incrys.internship.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ro.incrys.internship.entities.Question;
import services.QuestionService;
import services.WordContainsException;

/**
 * Servlet implementation class QuestionServlet adds a question to a quiz
 */
@WebServlet("/QuestionServlet")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuestionServlet() {
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
		QuestionService qs = new QuestionService();
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		int quizId = Integer.parseInt(request.getParameter("quizId"));
		boolean ordered;
		Question question = new Question();
		int questionId = 0;

		try {
			ordered = Boolean.parseBoolean(request
					.getParameterValues("ordered")[0]);

		} catch (Exception e) {
			Logger.getLogger(QuestionServlet.class).debug(e.getMessage());
			ordered = false;
		}

		try {

			question.setQuizId(quizId);
			question.setTitle(title);
			question.setText(text);
			question.setOrdered(ordered);

			questionId = qs.createEntity(question);
		} catch (SQLException | WordContainsException e) {
			Logger.getLogger(QuestionServlet.class).debug(e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorM", "Could not add question to quiz");
			request.getRequestDispatcher("protected/admin/addQuestion.jsp")
					.forward(request, response);
			return;
		}

		request.setAttribute("questionId", questionId);
		request.setAttribute("notifyM", "Question Successfully Added");
		request.getRequestDispatcher("protected/admin/addAnswer.jsp").forward(
				request, response);

	}

}
