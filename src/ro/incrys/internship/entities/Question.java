/**
 * 
 */
package ro.incrys.internship.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;

import ro.incrys.internship.servlets.Util;
import services.WordContainsException;

/**
 * @author user
 * 
 */
@XmlRootElement
public class Question extends EntityImpl implements Serializable,Comparable<Question> {

	private static final long serialVersionUID = 1L;
	protected int quizId;
	protected String title;
	protected String text;
	protected boolean ordered;
	protected Collection<Answer> answers;
	protected Collection<CandidateAnswer> candidateAnswers;

	public Question() {

	}

	public Question(int id, int quizId, String title, String text,
			boolean ordered) {
		super(id);
		this.quizId = quizId;
		this.title = title;
		this.text = text;
		this.ordered = ordered;
		if (ordered) {
			this.answers = new LinkedList<Answer>();
			this.candidateAnswers = new LinkedList<CandidateAnswer>();
		} else {
			this.answers = new HashSet<Answer>();
			this.candidateAnswers = new HashSet<CandidateAnswer>();
		}

	}

	public void addAnswer(Answer a) {
		answers.add(a);
	}

	public void removeAnswer(Answer a) {
		answers.remove(a);
	}

	public void addCandidateAnswer(CandidateAnswer ca) {
		this.candidateAnswers.add(ca);
	}

	public void removeCandidateAnswer(CandidateAnswer ca) {
		this.candidateAnswers.remove(ca);
	}

	/**
	 * @return the quizId
	 */
	public int getQuizId() {
		return quizId;
	}

	/**
	 * @param quizId
	 *            the quizId to set
	 */
	public void setQuizId(int quizId) {
		if (quizId > 0)
			this.quizId = quizId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {

		this.title = title;
	}

	/**
	 * @return the text
	 * @throws WordContainsException
	 */
	public String getText() {

		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) throws WordContainsException {
		Util.verifyParameter(text);
		this.text = text;
	}

	/**
	 * @return the ordered
	 */
	public boolean getOrdered() {
		return ordered;
	}

	/**
	 * @param ordered
	 *            the ordered to set
	 */
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

	/**
	 * @return the answers
	 */
	public Collection<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers
	 *            the answers to set
	 */
	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Question [quizId=" + quizId + ", title=" + title + ", text="
				+ text + ", ordered=" + ordered + ", answers=" + answers
				+ ", candidateAnswers=" + candidateAnswers + "]";
	}

	@Override
	public int compareTo(Question o) {
		return this.getId()-o.getId();
	}

}
