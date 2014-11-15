/**
 * 
 */
package ro.incrys.internship.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author user
 *
 */
@XmlRootElement
public class CandidateAnswer extends EntityImpl implements Serializable{

	private static final long serialVersionUID = 1L;
	protected int testId;
	protected int questionId;
	protected int answerId;

	public CandidateAnswer(){
		
	}
	public CandidateAnswer(int id,int testId, int questionId, int answerId) {
		super(id);
		this.testId = testId;
		this.questionId = questionId;
		this.answerId = answerId;
	}

	/**
	 * @return the testId
	 */
	public int getTestId() {
		return testId;
	}

	/**
	 * @param testId
	 *            the testId to set
	 */
	public void setTestId(int testId) {
		this.testId = testId;
	}

	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId
	 *            the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the answerId
	 */
	public int getAnswerId() {
		return answerId;
	}

	/**
	 * @param answerId
	 *            the answerId to set
	 */
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CandidateAnswer [testId=" + testId + ", questionId="
				+ questionId + ", answerId=" + answerId + "]";
	}
	
	

}
