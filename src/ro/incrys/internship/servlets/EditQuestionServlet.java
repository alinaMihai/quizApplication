package ro.incrys.internship.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ro.incrys.internship.entities.Answer;
import ro.incrys.internship.entities.Question;
import ro.incrys.internship.entities.Quiz;
import services.AnswerService;
import services.QuestionService;
import services.QuizService;
import services.WordContainsException;

/**
 * Servlet implementation class EditQuestionServlet
 */
@WebServlet("/EditQuestionServlet")
public class EditQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditQuestionServlet() {
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

		AnswerService answerService = new AnswerService();
		QuestionService qs = new QuestionService();
		QuizService quizService = new QuizService();
		String title = request.getParameter("questionTitle");
		String text = request.getParameter("questionText");
		HttpSession session = request.getSession();
		boolean ordered;
		Question question = new Question();
		int quizId = Integer.parseInt(request.getParameter("quizId"));
		int questionId = Integer.parseInt(request.getParameter("questionId"));

		try {
			ordered = Boolean.parseBoolean(request
					.getParameterValues("answersOrdered")[0]);

		} catch (Exception e) {
			Logger.getLogger(EditQuestionServlet.class).debug(e.getMessage());
			ordered = false;
		}
		try {
			// update question answers
			Enumeration<String> parameterNames = request.getParameterNames();
			Collection<String> paramNames = Collections.list(parameterNames);

			for (String paramName : paramNames) {

				if (paramName.contains("aId")) {

					boolean correct = false;
					int answerId = Integer.parseInt(paramName
							.substring(paramName.indexOf("d") + 1));
					String textAnswer = request.getParameter("aId" + answerId);

					if (request.getParameter("correct" + answerId) != null) {

						correct = Boolean.parseBoolean(request
								.getParameter("correct" + answerId));
					}
					Answer answer = new Answer();
					answer.setId(answerId);
					answer.setCorrect(correct);
					answer.setQuestionId(questionId);
					answer.setText(textAnswer);

					answerService.updateEntity(answer);
				}
			}

			question.setId(questionId);
			question.setTitle(title);
			question.setText(text);
			question.setOrdered(ordered);

			qs.updateEntity(question);

		} catch (WordContainsException e) {
			Logger.getLogger(EditQuestionServlet.class).debug(e.getMessage());
			e.printStackTrace();
			session.setAttribute("errorM", "Could not update question");
			response.sendRedirect("protected/admin/addQuestion.jsp");
			return;
		}

		session.setAttribute("notifyM", "Question Successfully updated");
		// update quiz in session variable
		Quiz quiz = quizService.getEntityById(quizId);
		ArrayList<Question> sortedQuestions = quiz.sortQuestions(quiz
				.getQuestions());
		quiz.setQuestions(sortedQuestions);

		session.setAttribute("quiz", quiz);

		response.sendRedirect("protected/admin/editQuiz.jsp");

	}

}
