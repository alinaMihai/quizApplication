package ro.incrys.internship.DatabaseManager;

/**
 * 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import ro.incrys.internship.servlets.AddQuizToCandidateServlet;

/**
 * @author Alina
 * 
 */
public class DBManager {

	/**
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {

		Connection conn = null;
		Properties connectionProps = new Properties();
		InputStream is;

		try {

			is = new FileInputStream(
					"D:\\incrys internship\\workspace\\QuizAppWorkProject5\\dbdetails.properties");

			connectionProps.load(is);
			is.close();
			Class.forName(connectionProps.getProperty("driverclass"));
			conn = DriverManager.getConnection(
					connectionProps.getProperty("url"), connectionProps);

		} catch (IOException | ClassNotFoundException | SQLException e) {
			Logger.getLogger(DBManager.class).debug(
					e.getMessage());
			e.printStackTrace();
		}

		return conn;
	}

	public static void closeConnection(Connection conn) {

		if (conn != null) {

			try {
				conn.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException
	 *             If something goes wrong
	 */
	public static ResultSet executeQuery(Connection conn, String sql)
			throws SQLException {
		Statement stmt = null;

		stmt = conn.createStatement();
		return stmt.executeQuery(sql);
	}

}