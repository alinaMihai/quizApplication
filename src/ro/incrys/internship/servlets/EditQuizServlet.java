package ro.incrys.internship.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ro.incrys.internship.entities.Question;
import ro.incrys.internship.entities.Quiz;
import services.QuestionService;
import services.QuizService;
import services.WordContainsException;

/**
 * Servlet implementation class EditQuizServlet
 *  edit quiz information, question information, delete quiz, delete question
 */
public class EditQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditQuizServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		int quizId = Integer.parseInt(request.getParameter("id"));

		QuizService qs = new QuizService();
		Quiz quiz = qs.getEntityById(quizId);

		// sort questions for edit
		ArrayList<Question> sortedQuestions = quiz.sortQuestions(quiz
				.getQuestions());
		quiz.setQuestions(sortedQuestions);

		session.setAttribute("quiz", quiz);
	
		response.sendRedirect("protected/admin/editQuiz.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		QuizService quizService = new QuizService();
		int quizId = Integer.parseInt(request.getParameter("quizId"));
		Quiz quiz = null;

		//deleteQuiz case 
		if(request.getParameter("deleteQuiz")!=null){
			quizService.deleteEntity(quizId);
			session.setAttribute("notifyM", "Quiz successfully deleted");
		
			// update quizes list
			Collection<Quiz> quizes = quizService.getEntityList();

			session.setAttribute("quizes", quizes);

			response.sendRedirect("protected/admin/editQuizes.jsp");
			return;
		}
		
		// addQuestion case

		if (request.getParameter("addQuestion") != null) {
			quiz = quizService.getEntityById(quizId);
			session.setAttribute("quizType", quiz.getType());
			request.setAttribute("quizId", quizId);
			request.getRequestDispatcher("protected/admin/addQuestion.jsp")
					.forward(request, response);
			return;
		}

		// editQuestion case
		if (request.getParameter("editQuestion") != null) {
			int questionId = Integer.parseInt(request
					.getParameter("editQuestion"));

			QuestionService questionService = new QuestionService();
			Question question = questionService.getEntityById(questionId);

			request.setAttribute("question", question);
			// redirect to edit question
			request.getRequestDispatcher("protected/admin/editQuestion.jsp")
					.forward(request, response);
			return;

			// delete question case
		} else if (request.getParameter("deleteQuestion") != null) {
			int questionId = Integer.parseInt(request
					.getParameter("deleteQuestion"));
			QuestionService questionService = new QuestionService();
			questionService.deleteEntity(questionId);

			// get the updated quiz, without the deleted question
			quiz = quizService.getEntityById(quizId);

			session.setAttribute("notifyM", "Question successfully deleted");
			session.setAttribute("quiz", quiz);

			// update quizes list
			Collection<Quiz> quizes = quizService.getEntityList();

			session.setAttribute("quizes", quizes);

			response.sendRedirect("protected/admin/editQuiz.jsp");
			return;
		}

		// update quiz case

		String qName = request.getParameter("quizName");

		boolean ordered;
		int type = 0;
		try {
			ordered = Boolean.parseBoolean(request
					.getParameterValues("ordered")[0]);

		} catch (Exception e) {
			Logger.getLogger(EditQuizServlet.class).debug(e.getMessage());
			ordered = false;
		}

		int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
		if (request.getParameter("type") != null)
			type = Integer.parseInt(request.getParameter("type"));
		String comments = request.getParameter("comments");

		try {
			Quiz q = new Quiz();
			q.setId(quizId);
			q.setName(qName);
			q.setOrdered(ordered);
			q.setTimeLimit(timeLimit);
			q.setType(type);
			q.setModifiedDate(new Timestamp(new Date().getTime()));
			q.setComments(comments);

			quizService.updateEntity(q);

		} catch (WordContainsException e) {
			Logger.getLogger(EditQuizServlet.class).debug(e.getMessage());
			e.printStackTrace();
			session.setAttribute("errorM",
					"Could not update quiz. \n A Quiz with  name " + qName
							+ " already exits");

			response.sendRedirect("protected/admin/editQuiz.jsp");

			return;
		}
		// refresh the quizes list after update of a quiz
		session.setAttribute("quizes", quizService.getEntityList());
		session.setAttribute("notifyM", "Quiz successfully updated");
		response.sendRedirect("protected/admin/editQuizes.jsp");

	}

}
