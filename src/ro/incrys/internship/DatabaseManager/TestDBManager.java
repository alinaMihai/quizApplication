package ro.incrys.internship.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import ro.incrys.internship.entities.Test;

/**
 * @author Alina
 * 
 */
public class TestDBManager {

	public static Test createTest(int candidateId, int quizId)
			throws ClassNotFoundException, SQLException {

		DBManager dbManager = new DBManager();
		Connection conn = dbManager.getConnection();
		Statement stm = conn.createStatement();
		String query = "INSERT INTO test(quiz_id,candidate_id) VALUES("
				+ quizId + "," + candidateId + ")";

		stm.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		ResultSet keys = stm.getGeneratedKeys();
		int lastKey = 1;

		// get the id after the insert
		while (keys.next()) {
			lastKey = keys.getInt(1);
		}

		Test t = new Test(lastKey, candidateId, quizId, null, null, 0.0);
		DBManager.closeConnection(conn);
		return t;
	}

	public static int createCandidateAnswer(int testId, int questionId,
			int answerId) throws ClassNotFoundException, SQLException {

		DBManager dbManager = new DBManager();
		Connection conn = dbManager.getConnection();

		Statement stm = conn.createStatement();
		String query = "INSERT INTO candidate_answer(test_id,question_id,answer_id) VALUES("
				+ testId + ",'" + questionId + "'," + answerId + ")";

		int ok = stm.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

		DBManager.closeConnection(conn);

		return ok;

	}

	public static boolean checkCorrectAnswer(int answerId) {

		DBManager dbManager = new DBManager();
		Connection conn = null;

		String query = "Select correct from answers where id=" + answerId;
		try {

			conn = dbManager.getConnection();
			ResultSet rs = DBManager.executeQuery(conn, query);

			while (rs.next()) {
				return rs.getBoolean("correct");
			}

		} catch (SQLException e) {

			Logger.getLogger(TestDBManager.class).debug(e.getMessage());
			e.printStackTrace();

		} finally {
			DBManager.closeConnection(conn);
		}

		return false;
	}

	/**
	 * @name getMultipleCorrectAnswers
	 * @param questionId  the id of a question in the database
	 * @return int the number of correct answers of a question in a multiple
	 *         choice quiz
	 */
	public static int getMultipleCorrectAnswers(int questionId) {

		DBManager dbManager = new DBManager();
		Connection conn = null;

		String query = "Select count(id) as ca From answers where correct=1 and question_id="
				+ questionId;
		try {

			conn = dbManager.getConnection();
			ResultSet rs = DBManager.executeQuery(conn, query);

			while (rs.next()) {

				return rs.getInt("ca");
			}

		} catch (SQLException e) {

			Logger.getLogger(TestDBManager.class).debug(e.getMessage());
			e.printStackTrace();

		} finally {

			DBManager.closeConnection(conn);
		}

		return 0;
	}

	public static void updateStartTest(int testId, Timestamp startTime) {

		DBManager dbManager = new DBManager();
		Connection conn = null;
		Statement stm;

		try {

			conn = dbManager.getConnection();
			stm = conn.createStatement();
			String query = "UPDATE test SET start_time='" + startTime
					+ "' WHERE id=" + testId;

			stm.executeUpdate(query);

		} catch (SQLException e) {

			Logger.getLogger(TestDBManager.class).debug(e.getMessage());
			e.printStackTrace();

		} finally {
			DBManager.closeConnection(conn);
		}

	}

	public static void updateTest(int testId, Timestamp endTime, double score)
			throws ClassNotFoundException {

		DBManager dbManager = new DBManager();
		Connection conn = null;
		Statement stm;

		try {
			conn = dbManager.getConnection();
			stm = conn.createStatement();
			String query = "UPDATE test SET end_time='" + endTime + "', score="
					+ score + " WHERE id=" + testId;

			stm.executeUpdate(query);

		} catch (SQLException e) {

			Logger.getLogger(TestDBManager.class).debug(e.getMessage());
			e.printStackTrace();

		} finally {

			DBManager.closeConnection(conn);
		}

	}

	/**
	 * @name getTestInformation computes the duration of a test in miliseconds
	 * @param testId
	 * @return long the duration of a test in miliseconds
	 */
	public static long getTestInformation(int testId) {

		DBManager dbManager = new DBManager();
		Connection conn = null;
		String query = "Select start_time, end_time FROM test where id="
				+ testId;
		Timestamp startTime = null, endTime = null;

		try {

			conn = dbManager.getConnection();
			ResultSet rs;

			rs = DBManager.executeQuery(conn, query);

			while (rs.next()) {

				startTime = rs.getTimestamp("start_time");
				endTime = rs.getTimestamp("end_time");

			}
		} catch (SQLException e) {
			Logger.getLogger(TestDBManager.class).debug(e.getMessage());
			e.printStackTrace();

		} finally {
			DBManager.closeConnection(conn);
		}

		long duration = endTime.getTime() - startTime.getTime();

		return TimeUnit.MILLISECONDS.toMinutes(duration);
	}

	/**
	 * @name findTestIdOfQuizId finds the testId that needs to be updated
	 *        when the test starts and ends
	 * @param quizId
	 * @param candidateId
	 * @return int the testId of a test belonging to a candidate
	 */
	public static int findTestIdOfQuizId(int quizId, int candidateId) {

		DBManager dbManager = new DBManager();
		Connection conn = null;
		PreparedStatement stm;

		try {

			conn = dbManager.getConnection();
			stm = conn
					.prepareStatement("Select id from test where quiz_id=? and candidate_id=?");
			stm.setInt(1, quizId);
			stm.setInt(2, candidateId);
			ResultSet rs = stm.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("id");
			}

		} catch (SQLException e) {
			Logger.getLogger(TestDBManager.class).debug(e.getMessage());
			e.printStackTrace();
		
		} finally {
			DBManager.closeConnection(conn);
		}
		return 0;
	}
}
