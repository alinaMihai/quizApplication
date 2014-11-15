/**
 * 
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.DBManager;
import ro.incrys.internship.entities.CandidateAnswer;
import ro.incrys.internship.entities.Test;

/**
 * @author user
 *
 */
public class TestService extends EntityCRUDService<Test> {

	@Override
	public int createEntity(Test entity) {
		// TODO Auto-generated method stub
        return 0;
	}

	@Override
	public List<Test> getEntityList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Test getEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void putCandidateAnswers(Test t) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Select * from candidate_answer where test_id=?");
			prep.setInt(1, t.getId());
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				CandidateAnswer ca = new CandidateAnswer(rs.getInt("id"),
						rs.getInt("test_id"), rs.getInt("question_id"),
						rs.getInt("answer_id"));
				t.addCandidateAnswer(ca);
			}
		} catch (Exception e) {
			Logger.getLogger(TestService.class).debug(e.getMessage());
		}
	}

	@Override
	public void updateEntity(Test entity) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Update test Set candidate_id=?,quiz_id=?,start_time=?,end_time=? where id=?");
			prep.setInt(1, entity.getCandidateId());
			prep.setInt(2, entity.getQuizId());
			prep.setTimestamp(6, entity.getStartTime());
			prep.setTimestamp(7, entity.getEndTime());
			prep.setInt(8, entity.getId());
			prep.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(TestService.class).debug(e.getMessage());
            e.printStackTrace();
		} finally {
			DBManager.closeConnection(con);
		}

	}

	@Override
	public void deleteEntity(int id) {
		DBManager db = new DBManager();
		Connection con = null;
		PreparedStatement stm=null;
		try {
			con = db.getConnection();
			
			//delete candidate answers
			 stm=con.prepareStatement("Delete from candidate_answer where test_id=?");
			 stm.setInt(1,id);
			 stm.executeUpdate();
			 
			//delete the test itself 
			 stm = con.prepareStatement("Delete from test Where id=?");
			stm.setInt(1, id);
			stm.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(TestService.class).debug(e.getMessage());
		} finally {
			DBManager.closeConnection(con);
		}

	}

}
