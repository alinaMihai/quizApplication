/**
 * 
 */
package ro.incrys.internship.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import ro.incrys.internship.servlets.Util;
import services.WordContainsException;

/**
 * @author user
 *
 */
@XmlRootElement
public class Answer extends EntityImpl implements Serializable{


	private static final long serialVersionUID = 1L;
	protected int questionId;
	protected String text;
	protected boolean correct;

	public Answer(){
		
	}
	public Answer(int id,int questionId, String text, boolean correct) {
		super(id);
		this.questionId = questionId;
		this.text = text;
		this.correct = correct;
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
		if(questionId>0)
		this.questionId = questionId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 * @throws WordContainsException 
	 */
	public void setText(String text) throws WordContainsException {
		Util.verifyParameter(text);
		this.text = text;
		
	}

	/**
	 * @return the correct
	 */
	public boolean isCorrect() {
		return correct;
	}

	/**
	 * @param correct
	 *            the correct to set
	 */
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Answer [questionId=" + questionId + ", text=" + text
				+ ", correct=" + correct + "]";
	}
	
	

}
