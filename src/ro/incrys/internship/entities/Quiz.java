/**
 * 
 */
package ro.incrys.internship.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ro.incrys.internship.servlets.Util;
import services.WordContainsException;

/**
 * @author user
 * 
 */
@XmlRootElement
public class Quiz extends EntityImpl implements Serializable, Comparable<Quiz> {

	private static final long serialVersionUID = 1L;
	protected String name;
	protected boolean ordered;
	protected int timeLimit;

	protected int type;

	protected String comments;
	
	protected Timestamp createdDate;
	protected Timestamp modifiedDate;
	protected Collection<Question> questions;
	protected Collection<Test> tests;

	public Quiz() {
	}

	public Quiz(int id, String name, boolean ordered, int timeLimit, int type,
			String comments, Timestamp createdDate, Timestamp modifiedDate) {
		super(id);
		this.name = name;
		this.ordered = ordered;

		if (this.ordered) {
			this.questions = new LinkedList<Question>();
			this.tests = new LinkedList<Test>();
		} else {
			this.questions = new HashSet<Question>();
			this.tests = new HashSet<Test>();
		}
		this.timeLimit = timeLimit;
		this.type = type;
		this.comments = comments;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}


	public void addQuestion(Question q) {
		this.questions.add(q);
	}

	public void removeQuestion(Question q) {
		this.removeQuestion(q);
	}

	public void addTest(Test t) {
		this.tests.add(t);
	}

	public void removeTest(Test t) {
		this.tests.remove(t);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) throws WordContainsException {
		Util.verifyParameter(name);
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public boolean getOrdered() {
		return this.ordered;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

	/**
	 * @return the timeLimit
	 */
	public int getTimeLimit() {
		return timeLimit;
	}

	/**
	 * @param timeLimit
	 *            the timeLimit to set
	 */
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	@XmlJavaTypeAdapter( TimestampAdapter.class)
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	@XmlJavaTypeAdapter( TimestampAdapter.class)
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the questions
	 */
	public Collection<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the tests
	 */
	public Collection<Test> getTests() {
		return tests;
	}

	/**
	 * @param tests
	 *            the tests to set
	 */
	public void setTests(Collection<Test> tests) {
		this.tests = tests;
	}

	@Override
	public int compareTo(Quiz o) {
		return this.getId() - o.getId();
	}

	public ArrayList<Question> sortQuestions(Collection<Question> questionList) {
		ArrayList<Question> questionsSorted = new ArrayList<Question>(questionList);
		Collections.sort(questionsSorted);
		return questionsSorted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Quiz [id="+id+", name=" + name + ", ordered=" + ordered + ", timeLimit="
				+ timeLimit + ", type=" + type + ", comments=" + comments
				+ ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", questions=" + questions + ", tests="
				+ tests + "]";
	}

}
