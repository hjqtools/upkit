package upkit.bp.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Demo01 {

	public static void main(String[] args) {

		Connection connection = null;
		PreparedStatement pstem = null;

		String sql = null;
		try {
			connection.setAutoCommit(false);
			pstem = connection.prepareStatement(sql);
			pstem.setString(1, "sads");
			pstem.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
