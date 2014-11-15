package ro.incrys.internship.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ro.incrys.internship.entities.Quiz;
import services.QuizService;
import services.WordContainsException;

/**
 * Servlet implementation class CreateQuiz
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateQuizServlet() {
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
       QuizService qs=new QuizService();
		String qName = request.getParameter("qname");
		boolean ordered;
		int type = 0;
		try {
			ordered = Boolean.parseBoolean(request
					.getParameterValues("ordered")[0]);

		} catch (Exception e) {
			Logger.getLogger(CreateQuizServlet.class).debug(e.getMessage());
			ordered = false;
		}

		int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
		if (request.getParameter("type") != null)
			type = Integer.parseInt(request.getParameter("type"));
		String comments = request.getParameter("comments");
		int quizId = 0;
		HttpSession session = request.getSession();
		try {
			Quiz q=new Quiz();
			q.setName(qName);
			q.setOrdered(ordered);
			q.setTimeLimit(timeLimit);
			q.setType(type);
			q.setComments(comments);
			
			quizId = qs.createEntity(q);
			
		} catch (SQLException | WordContainsException e) {
			Logger.getLogger(CreateQuizServlet.class).debug(e.getMessage());
			session.setAttribute("errorM",
					"Could not create quiz. \n A Quiz with  name "+qName+" already exits");
		
			response.sendRedirect("protected/admin/createQuiz.jsp");

			return;
		}
        session.setAttribute("quizes",qs.getEntityList());
		session.setAttribute("quizId", quizId);
		session.setAttribute("quizType", type);
		session.setAttribute("quizName", qName);
		session.setAttribute("notifyM","Quiz successfully created");
		
		request.getRequestDispatcher("protected/admin/addQuestion.jsp")
				.forward(request, response);

	}

}
