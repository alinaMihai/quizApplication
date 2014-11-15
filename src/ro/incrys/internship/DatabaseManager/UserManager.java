package ro.incrys.internship.DatabaseManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import ro.incrys.internship.entities.Candidate;

public class UserManager {

	/**
	 * @getUser gets a specific candidate from the database if it exists
	 * @param username
	 * @param password
	 * @return Candidate the candidate that has the specified username and
	 *         password
	 * @throws ClassNotFoundException
	 */
	public static Candidate getUser(String username, String password)
			throws ClassNotFoundException {

		DBManager dbManager = new DBManager();
		Connection conn = null;
		Candidate c = null;
		String hashPassword = generateHashPassword(password);
		ResultSet rs;

		try {
			conn = dbManager.getConnection();

			PreparedStatement ps = conn
					.prepareStatement("Select * From candidate Where username=? and password=?");
			ps.setString(1, username);
			ps.setString(2, hashPassword);

			rs = ps.executeQuery();

			while (rs.next()) {
				c = new Candidate(rs.getInt("id"), rs.getString("name"),
						rs.getString("username"), rs.getString("password"),
						rs.getInt("role"));
			}

		} catch (SQLException e) {

			Logger.getLogger(UserManager.class).debug(e.getMessage());
			e.printStackTrace();

		} finally {
			DBManager.closeConnection(conn);
		}
		return c;
	}

	public static String generateHashPassword(String passwordToHash) {

		String generatedPassword = null;

		try {

			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// Add password bytes to digest
			md.update(passwordToHash.getBytes());

			// Get the hash's bytes
			byte[] bytes = md.digest();

			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

			// Get complete hashed password in hex format
			generatedPassword = sb.toString();

		} catch (NoSuchAlgorithmException e) {

			Logger.getLogger(UserManager.class).debug(e.getMessage());
			e.printStackTrace();

		}
		return generatedPassword;
	}

	public static int createUser(String name, String username, String password) {

		String hashedPassword = generateHashPassword(password);
		DBManager dbManager = new DBManager();
		Connection conn = null;
		int lastKey = 1;

		try {

			conn = dbManager.getConnection();
			Statement stm = conn.createStatement();
			String query = "INSERT INTO candidate(name,username,password) VALUES('"
					+ name + "','" + username + "','" + hashedPassword + "')";

			stm.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet keys = stm.getGeneratedKeys();

			while (keys.next()) {
				lastKey = keys.getInt(1);
			}

		} catch (Exception e) {
			Logger.getLogger(UserManager.class).debug(e.getMessage());
			e.printStackTrace();

		} finally {
			DBManager.closeConnection(conn);
		}
		return lastKey;
	}
}
