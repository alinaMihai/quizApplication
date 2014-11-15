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
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.DBManager;
import ro.incrys.internship.DatabaseManager.UserManager;
import ro.incrys.internship.entities.Candidate;
import ro.incrys.internship.entities.Quiz;
import ro.incrys.internship.entities.Test;

/**
 * @author user
 *
 */
public class CandidateService extends EntityCRUDService<Candidate> {

	@Override
	public int createEntity(Candidate entity) {
		// TODO Auto-generated method stub
     return 0;
	}

	@Override
	public List<Candidate> getEntityList() {
		List<Candidate> candidates = new ArrayList<Candidate>();
		DBManager dbManager = new DBManager();
		Connection con = dbManager.getConnection();
		Statement stm;
		try {
			stm = con.createStatement();
			ResultSet rs = stm
					.executeQuery("Select id,name,username from candidate where role=0");
			while (rs.next()) {
				Candidate c = new Candidate(rs.getInt("id"),
						rs.getString("name"), rs.getString("username"), "", 0);
				this.putTests(c);
				candidates.add(c);
			}
		} catch (SQLException e) {
			Logger.getLogger(CandidateService.class).debug(e.getMessage());
			e.printStackTrace();
		}

		return candidates;
	}

	public void putTests(Candidate c) {
		DBManager dbManager = new DBManager();
		Connection con = dbManager.getConnection();

		try {
			PreparedStatement ps = con
					.prepareStatement("Select * from test where candidate_id=?");
			ps.setInt(1, c.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				Test t = new Test(rs.getInt("id"), rs.getInt("candidate_id"),
						rs.getInt("quiz_id"), rs.getTimestamp("start_time"),
						rs.getTimestamp("end_time"), rs.getDouble("score"));
				t.setQuizName(QuizService.getQuizName(rs.getInt("quiz_id")));
			
				TestService.putCandidateAnswers(t);
				
				c.addTest(t);
			}
		} catch (SQLException e) {
			Logger.getLogger(CandidateService.class).debug(e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public Candidate getEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEntity(Candidate entity) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Update candidate Set name=?,username=?,password=?,role=?");
			prep.setString(1, entity.getName());
			prep.setString(2, entity.getUsername());

			prep.setString(3,
					UserManager.generateHashPassword(entity.getPassword()));
			prep.setInt(4, entity.getRole());

			prep.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(CandidateService.class).debug(e.getMessage());
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
					.prepareStatement("Delete from candidate Where id=?");
			stm.setInt(1, id);
			stm.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(CandidateService.class).debug(e.getMessage());
		} finally {
			DBManager.closeConnection(con);
		}

	}

	public static Collection<Quiz> getCandidateQuizes(int candidateId) {
		Collection<Quiz> quizes = new LinkedList<Quiz>();
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement stm = con
					.prepareStatement("Select * From quizes Join test on quizes.id=test.quiz_id Where test.candidate_id=? and start_time is null");
			stm.setInt(1, candidateId);
			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("id"), rs.getString("name"),
						rs.getBoolean("ordered"), rs.getInt("time_limit"),
						rs.getInt("type"), rs.getString("comments"),
						rs.getTimestamp("created_date"),
						rs.getTimestamp("modified_date"));
				QuizService.loadQuestions(quiz);
				quizes.add(quiz);

			}
		} catch (Exception e) {
			Logger.getLogger(CandidateService.class).debug(e.getMessage());
		} finally {
			DBManager.closeConnection(con);
		}
		return quizes;
	}

}
