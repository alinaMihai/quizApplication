package ro.incrys.internship.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ro.incrys.internship.entities.Answer;
import services.AnswerService;

/**
 * Servlet implementation class AddAnswer
 *   add possible answers to a question
 *  whether it belongs to a multiple answers quiz or simple one 
 */
@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnswerServlet() {
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
	
		AnswerService as = new AnswerService();
		int questionId = Integer.parseInt(request.getParameter("questionId"));

		for (int i = 1; i <= 5; i++) {
			boolean correct = false;
			try {

				String text = request.getParameter("a" + i);
				if (!text.equals("")) {

					if (request.getParameterValues("check") != null) {
						for (String answer : request
								.getParameterValues("check")) {
							if (Integer.parseInt(answer) == i) {
								correct = true;
							}
						}

					} else if (request.getParameter("radio") != null) {
						if (Integer.parseInt(request.getParameter("radio")) == i) {
							correct = true;
						}
					}
					Answer answer = new Answer();
					answer.setQuestionId(questionId);
					answer.setText(text);
					answer.setCorrect(correct);
					//create answer
					as.createEntity(answer);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Logger.getLogger(AnswerServlet.class).error(e.getMessage());
			}
		}
		request.setAttribute("notifyM", "Possible answers added");
		request.setAttribute("questionId", questionId);
		request.getRequestDispatcher("protected/admin/addQuestion.jsp")
				.forward(request, response);

	}

}
