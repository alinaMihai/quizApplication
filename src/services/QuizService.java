/**
 * 
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.DBManager;
import ro.incrys.internship.entities.Question;
import ro.incrys.internship.entities.Quiz;
import ro.incrys.internship.entities.Test;

/**
 * @author user
 * 
 */
public class QuizService extends EntityCRUDService<Quiz> {

	@Override
	public int createEntity(Quiz entity) throws SQLException {
		DBManager dbManager = new DBManager();
		Connection conn = null;
		PreparedStatement stm;
		int lastKey = 0;

		conn = dbManager.getConnection();
		String sql = "INSERT INTO quizes(name,ordered,time_limit,type,comments) VALUES(?,?,?,?,?)";

		stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stm.setString(1, entity.getName());
		stm.setBoolean(2, entity.getOrdered());
		stm.setInt(3, entity.getTimeLimit());
		stm.setInt(4, entity.getType());
		stm.setString(5, entity.getComments());
		stm.executeUpdate();
		ResultSet keys = stm.getGeneratedKeys();
		lastKey = 1;
		while (keys.next()) {
			lastKey = keys.getInt(1);
		}
		DBManager.closeConnection(conn);

		return lastKey;

	}

	@Override
	public List<Quiz> getEntityList() {
		List<Quiz> quizes = new ArrayList<Quiz>();
		DBManager db = new DBManager();
		Connection con = null;

		try {
			con = db.getConnection();
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("Select * from quizes");
			while (rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("id"), rs.getString("name"),
						rs.getBoolean("ordered"), rs.getInt("time_limit"),
						rs.getInt("type"), rs.getString("comments"),
						rs.getTimestamp("created_date"),
						rs.getTimestamp("modified_date"));
				loadQuestions(quiz);
				quizes.add(quiz);
			}
			return quizes;
		} catch (Exception e) {
			Logger.getLogger(QuizService.class).debug(e.getMessage());
		} finally {
			DBManager.closeConnection(con);
		}

		return null;
	}

	@Override
	public Quiz getEntityById(int id) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Select * from quizes Where id=?");
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("id"), rs.getString("name"),
						rs.getBoolean("ordered"), rs.getInt("time_limit"),
						rs.getInt("type"), rs.getString("comments"),
						rs.getTimestamp("created_date"),
						rs.getTimestamp("modified_date"));
				loadQuestions(quiz);
				return quiz;
			}
		} catch (Exception e) {
			Logger.getLogger(QuizService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}
		return null;
	}

	public static void loadQuestions(Quiz quiz) {
		DBManager db = new DBManager();
		Connection con = null;

		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Select * from questions where quiz_id=?");
			prep.setInt(1, quiz.getId());
			ResultSet questionsRS = prep.executeQuery();
			while (questionsRS.next()) {
				Question question = new Question(questionsRS.getInt("id"),
						questionsRS.getInt("quiz_id"),
						questionsRS.getString("title"),
						questionsRS.getString("text"),
						questionsRS.getBoolean("ordered"));
				QuestionService.loadPossibleAnswers(question);
				quiz.getQuestions().add(question);
			}
		} catch (SQLException e) {
			Logger.getLogger(QuizService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}
	}

	public static void loadTests(Quiz quiz) {
		DBManager db = new DBManager();
		Connection con = null;

		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Select * from test where quiz_id=?");
			prep.setInt(1, quiz.getId());
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				Test test = new Test(rs.getInt("id"),
						rs.getInt("candidate_id"), rs.getInt("quiz_id"),
						rs.getTimestamp("start_time"),
						rs.getTimestamp("end_time"), rs.getFloat("score"));
				quiz.getTests().add(test);
			}
		} catch (SQLException e) {
			Logger.getLogger(QuizService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}
	}

	@Override
	public void updateEntity(Quiz entity) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Update quizes Set name=?,ordered=?,time_limit=?,type=?,comments=?,modified_date=? where id=?");
			prep.setString(1, entity.getName());
			prep.setBoolean(2, entity.getOrdered());
			prep.setInt(3, entity.getTimeLimit());
			prep.setInt(4, entity.getType());
			prep.setString(5, entity.getComments());
			prep.setTimestamp(6, entity.getModifiedDate());
			prep.setInt(7, entity.getId());
			prep.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(QuizService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}

	}

	@Override
	public void deleteEntity(int id) {
		DBManager db = new DBManager();
		Connection con = null;
		Quiz quiz = this.getEntityById(id);
		loadTests(quiz);

		try {
			con = db.getConnection();
			// delete tests
			TestService testService = new TestService();
			Collection<Test> tests = quiz.getTests();
			for (Test test : tests) {
				testService.deleteEntity(test.getId());
			}
			// delete questions
			QuestionService questionService = new QuestionService();
			for (Question question : quiz.getQuestions()) {
				questionService.deleteEntity(question.getId());
			}
			PreparedStatement stm = con
					.prepareStatement("Delete from quizes Where id=?");
			stm.setInt(1, id);
			stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(QuizService.class).debug(e.getMessage());
		} finally {
			DBManager.closeConnection(con);
		}

	}

	public static String getQuizName(int quizId) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement stm = con
					.prepareStatement("Select name from quizes where id=?");
			stm.setInt(1, quizId);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getString("name");
			}
		} catch (Exception e) {
			Logger.getLogger(QuizService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}
		return null;
	}

	public Collection<Quiz> getNewQuizesForCandidate(int candidateId) {
		Collection<Quiz> quizes = new HashSet<Quiz>();
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement stm = con
					.prepareStatement("Select * From quizes Where id Not In(Select quiz_id From test Where candidate_id=?)");
			stm.setInt(1, candidateId);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Quiz quiz = new Quiz();
				quiz.setId(rs.getInt("id"));
				quiz.setName(rs.getString("name"));
				quizes.add(quiz);
			}
		} catch (Exception e) {
			Logger.getLogger(QuizService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}
		return quizes;

	}

}
