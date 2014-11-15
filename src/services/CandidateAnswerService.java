/**
 * 
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.log4j.Logger;

import ro.incrys.internship.DatabaseManager.DBManager;
import ro.incrys.internship.DatabaseManager.UserManager;
import ro.incrys.internship.entities.CandidateAnswer;

/**
 * @author user
 *
 */
public class CandidateAnswerService extends EntityCRUDService<CandidateAnswer> {

	@Override
	public int createEntity(CandidateAnswer entity) {
		// TODO Auto-generated method stub
      return 0;
	}

	@Override
	public List<CandidateAnswer> getEntityList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CandidateAnswer getEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEntity(CandidateAnswer entity) {
		DBManager db = new DBManager();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement prep = con
					.prepareStatement("Update candidate_answer Set test_id=?,question_id=?,answer_id=?");
			prep.setInt(1, entity.getTestId());
			prep.setInt(2, entity.getQuestionId());

			prep.setInt(3, entity.getAnswerId());

			prep.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(CandidateAnswerService.class).debug(e.getMessage());
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
					.prepareStatement("Delete from candidate_answer Where id=?");
			stm.setInt(1, id);
			stm.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(CandidateAnswerService.class).debug(e.getMessage());
		} finally {
			DBManager.closeConnection(con);
		}
	}

}
