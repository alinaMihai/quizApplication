/**
 * 
 */
package ro.incrys.internship.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author user
 *
 */
@XmlRootElement
public class Test extends EntityImpl implements Serializable{

	
	private static final long serialVersionUID = 1L;
	protected int candidateId;
	protected int quizId;
	
	protected Timestamp startTime;
	protected Timestamp endTime;
	protected double score;
	private String quizName;
	protected Collection<CandidateAnswer> candidateAnswers;

	public Test(){
		
	}
	public Test(int id, int candidateId, int quizId, Timestamp startTime,
			Timestamp endTime, double score) {
		super(id);
		this.candidateId = candidateId;
		this.quizId = quizId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.score = score;
		this.candidateAnswers = new HashSet<CandidateAnswer>();
	}

	public void addCandidateAnswer(CandidateAnswer ca) {
		this.candidateAnswers.add(ca);
	}

	public void removeCandidateAnswer(CandidateAnswer ca) {
		this.candidateAnswers.remove(ca);
	}

	/**
	 * @return the candidateId
	 */
	public int getCandidateId() {
		return candidateId;
	}

	/**
	 * @param candidateId
	 *            the candidateId to set
	 */
	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
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
		this.quizId = quizId;
	}

	/**
	 * @return the startTime
	 */
	@XmlJavaTypeAdapter( TimestampAdapter.class)
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	@XmlJavaTypeAdapter( TimestampAdapter.class)
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return the candidateAnswers
	 */
	public Collection<CandidateAnswer> getCandidateAnswers() {
		return candidateAnswers;
	}

	/**
	 * @param candidateAnswers
	 *            the candidateAnswers to set
	 */
	public void setCandidateAnswers(Collection<CandidateAnswer> candidateAnswers) {
		this.candidateAnswers = candidateAnswers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public String getQuizName() {
		return quizName;
	}

	@Override
	public String toString() {
		return "Test [candidateId=" + candidateId + ", quizId=" + quizId
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", score=" + score + ", candidate_answers="
				+ candidateAnswers + "]";
	}

}
