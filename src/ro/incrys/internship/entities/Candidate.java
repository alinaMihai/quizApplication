/**
 * 
 */
package ro.incrys.internship.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlRootElement;

import ro.incrys.internship.servlets.Util;
import services.WordContainsException;

/**
 * @author user
 *
 */
@XmlRootElement
public class Candidate extends EntityImpl implements Serializable{


	private static final long serialVersionUID = 1L;
	protected String name;
	protected String username;
	protected String password;
	protected int role;
	protected Collection<Test> tests;

	public Candidate(){
		
	}
	
	public Candidate(int id, String name, String username, String password,
			int role) {
		super(id);
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
		this.tests = new HashSet<Test>();
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
	 * @throws WordContainsException 
	 */
	public void setName(String name) throws WordContainsException {
		Util.verifyParameter(name);
		this.name = name;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 * @throws WordContainsException 
	 */
	public void setUsername(String username) throws WordContainsException {
		Util.verifyParameter("username");
		this.username = username;
	}

	/**
	 * @return the password
	 */

	/**
	 * @return the tests
	 */
	public Collection<Test> getTests() {
		return tests;
	}

	/**
	 * @return the password
	 */

	/**
	 * @param tests
	 *            the tests to set
	 */
	public void setTests(Collection<Test> tests) {
		this.tests = tests;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 * @throws WordContainsException 
	 */
	public void setPassword(String password) throws WordContainsException {
		Util.verifyParameter(password);
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public int getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(int role) {
		this.role = role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Candidate [name=" + name + ", username=" + username
				+ ", password=" + password + ", tests=" + tests + "]";
	}

}
