package ro.incrys.internship.servlets;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ro.incrys.internship.entities.Candidate;
import ro.incrys.internship.entities.Quiz;
import services.CandidateService;
import services.QuizService;

/**
 * Servlet implementation class DisplayQuizesServlet
 */
@WebServlet("/DisplayQuizesServlet")
public class DisplayQuizesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @throws ClassNotFoundException
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayQuizesServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Collection<Quiz> quizes = null;
		HttpSession session = request.getSession();
		Candidate candidate = (Candidate) session.getAttribute("user");
		String path = "";
		int candidateId = 0;

		if (candidate != null)
			candidateId = candidate.getId();
		else
			response.sendRedirect("login.jsp");

		if (candidate.getRole() == 0) {
			quizes = CandidateService.getCandidateQuizes(candidateId);
			path = "protected/index.jsp";
			request.setAttribute("quizes", quizes);
			request.getRequestDispatcher(path).forward(request, response);
		} else {

			QuizService qs = new QuizService();
			quizes = qs.getEntityList();
			session.setAttribute("quizes", quizes);
			path = "protected/admin/editQuizes.jsp";
			response.sendRedirect(path);
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
