package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.DBManager;
import ro.incrys.internship.entities.Answer;
import ro.incrys.internship.servlets.UserServlet;

public class AnswerService extends EntityCRUDService<Answer> {

	@Override
	public int createEntity(Answer entity) {
		PreparedStatement stm = null;
		DBManager dbManager = new DBManager();
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			String sql = "INSERT INTO answers(question_id,text,correct) VALUES(?,?,?)";

			stm = conn.prepareStatement(sql);
			stm.setInt(1, entity.getQuestionId());
			stm.setString(2, entity.getText());
			stm.setBoolean(3, entity.isCorrect());
			stm.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(AnswerService.class).debug(e.getMessage());
			e.printStackTrace();
		} finally {
			DBManager.closeConnection(conn);
		}
		return 0;
	}

	@Override
	public List<Answer> getEntityList() {

		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			Statement stm = con.createStatement();
			stm.executeQuery("Select * from answers");
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(AnswerService.class).debug(e.getMessage());
		} finally {
			DBManager.closeConnection(con);
		}

		return null;
	}

	@Override
	public Answer getEntityById(int id) {
		DBManager db = new DBManager();

		try {
			Connection con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Select * from answers where id=?");
			prep.setInt(1, id);
			ResultSet res = prep.executeQuery();

			if (res.next()) {
				Answer a = new Answer(res.getInt("id"),
						res.getInt("questionId"), res.getString("text"),
						res.getBoolean("correct"));
				return a;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(AnswerService.class).debug(e.getMessage());
		}
		return null;
	}

	@Override
	public void updateEntity(Answer entity) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Update answers Set text=?,correct=? where id=?");

			prep.setString(1, entity.getText());
			prep.setBoolean(2, entity.isCorrect());
			prep.setInt(3, entity.getId());
			prep.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(AnswerService.class).debug(e.getMessage());
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
			PreparedStatement stm = con
					.prepareStatement("Delete from answers Where id=?");
			stm.setInt(1, id);
			stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(AnswerService.class).debug(e.getMessage());
		} finally {
			DBManager.closeConnection(con);
		}

	}

}
