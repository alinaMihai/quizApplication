package services;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ro.incrys.internship.DatabaseManager.DBManager;
import ro.incrys.internship.entities.Candidate;
import ro.incrys.internship.entities.CandidateAnswer;
import ro.incrys.internship.entities.Question;
import ro.incrys.internship.entities.Quiz;
import ro.incrys.internship.entities.Test;

public class CandidateServletHelper {
	private List<Candidate> candidates = null;

	protected void displayCandidates(HttpServletRequest request,
			HttpServletResponse response, int id) throws ServletException,
			IOException {

		CandidateService cs = new CandidateService();
		candidates = cs.getEntityList();
		HttpSession session = request.getSession();
		session.setAttribute("candidates", candidates);

		response.sendRedirect("protected/admin/viewCandidates.jsp");

	}

	protected void getTests(HttpServletRequest request,
			HttpServletResponse response, int candidateId)
			throws ServletException, IOException {
        CandidateService cs=new CandidateService();
        candidates=cs.getEntityList();
		Candidate c = findCandidateById(candidateId);

		request.setAttribute("tests", c.getTests());
		request.setAttribute("candidateName", c.getName());
		request.getRequestDispatcher("protected/admin/viewTests.jsp").forward(
				request, response);

	}

	public void getTestInfo(HttpServletRequest request,
			HttpServletResponse response, int testId) throws ServletException,
			IOException {
		QuizService qs = new QuizService();
		Test t = findTestById(testId);
		Quiz q = qs.getEntityById(t.getQuizId());
		t.setQuizName(q.getName());
		Collection<CandidateAnswer> candidateAnswers = t.getCandidateAnswers();
		Collection<Question> questions = q.getQuestions();
		HashMap<Integer, Integer> map = mapCandidateAnswersQuestions(questions,
				candidateAnswers);
		request.setAttribute("test", t);
		request.setAttribute("quizName", q.getName());
		request.setAttribute("questionsAnswersMap", map);
		request.setAttribute("questions", q.getQuestions());
	
		request.getRequestDispatcher("protected/admin/viewTestInfo.jsp")
				.forward(request, response);

	}

	private Candidate findCandidateById(int id) {
		if (candidates != null) {
			for (Candidate candidate : candidates) {
				if (candidate.getId() == id) {
					return candidate;
				}
			}
		}
		return null;
	}

	private Test findTestById(int id) {
		if (candidates != null) {
			for (Candidate candidate : candidates) {
				for (Test test : candidate.getTests()) {
					if (test.getId() == id) {
						return test;
					}
				}
			}
		}
		return null;
	}

	private int getAnswerIdByQuestionId(
			Collection<CandidateAnswer> candidateAnswers, int questionId) {
		for (CandidateAnswer answer : candidateAnswers) {
			if (answer.getQuestionId() == questionId) {
				return answer.getAnswerId();
			}
		}
		return 0;
	}

	private HashMap<Integer, Integer> mapCandidateAnswersQuestions(
			Collection<Question> questions,
			Collection<CandidateAnswer> candidateAnswers) {

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (Question question : questions) {
			int candidateAnswer = getAnswerIdByQuestionId(candidateAnswers,
					question.getId());
			map.put(question.getId(), candidateAnswer);
		}

		return map;
	}
}
