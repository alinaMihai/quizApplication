/**
 * 
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.DBManager;
import ro.incrys.internship.entities.Answer;
import ro.incrys.internship.entities.Question;

/**
 * @author user
 * 
 */
public class QuestionService extends EntityCRUDService<Question> {

	@Override
	public int createEntity(Question entity) throws SQLException {
		DBManager dbManager = new DBManager();
		Connection conn = null;
		PreparedStatement stm = null;
		int lastKey = 0;

		conn = dbManager.getConnection();
		String query = "INSERT INTO questions(quiz_id,title,text,ordered) VALUES(?,?,?,?)";
		stm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stm.setInt(1, entity.getQuizId());
		stm.setString(2, entity.getTitle());
		stm.setString(3, entity.getText());
		stm.setBoolean(4, entity.getOrdered());

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
	public List<Question> getEntityList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question getEntityById(int id) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Select * from questions where id=?");
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				Question question = new Question(id, rs.getInt("quiz_id"),
						rs.getString("title"), rs.getString("text"),
						rs.getBoolean("ordered"));
				loadPossibleAnswers(question);
				return question;
			}
		} catch (Exception e) {
			Logger.getLogger(QuestionService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}
		return null;
	}

	public static void loadPossibleAnswers(Question question) {
		DBManager db = new DBManager();
		Connection con = null;
		try {

			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Select * from answers where question_id=?");
			prep.setInt(1, question.getId());
			ResultSet answersRS = prep.executeQuery();
			while (answersRS.next()) {
				Answer answer = new Answer(answersRS.getInt("id"),
						answersRS.getInt("question_id"),
						answersRS.getString("text"),
						answersRS.getBoolean("correct"));
				question.getAnswers().add(answer);
			}
		} catch (Exception e) {
			Logger.getLogger(QuestionService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}

	}

	@Override
	public void updateEntity(Question entity) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Update questions Set title=?,text=?,ordered=? where id=?");

			prep.setString(1, entity.getTitle());
			prep.setString(2, entity.getText());
			prep.setBoolean(3, entity.getOrdered());
			prep.setInt(4, entity.getId());
			prep.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(QuestionService.class).debug(e.getMessage());
		} finally {
			DBManager.closeConnection(con);
		}

	}

	@Override
	public void deleteEntity(int id) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			// delete candidate_answers if any
			PreparedStatement deleteCandidateAnswers = con
					.prepareStatement("Delete From candidate_answer Where question_id=?");
			deleteCandidateAnswers.setInt(1, id);
			deleteCandidateAnswers.execute();
			
			// delete the answers of a question
			PreparedStatement deleteStm = con
					.prepareStatement("Delete from answers where question_id=?");
			deleteStm.setInt(1, id);
			deleteStm.execute();

			// delete the question itself
			PreparedStatement stm = con
					.prepareStatement("Delete from questions Where id=?");
			stm.setInt(1, id);
			stm.execute();

		} catch (Exception e) {
			Logger.getLogger(QuestionService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}

	}

}
