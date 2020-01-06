import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



public class JDBCMain {

	public static void main(String[] args) {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", "root");
		connectionProps.put("password", "db2019");
		
		try {
			conn = DriverManager.
					getConnection("jdbc:" + "mysql" + "://" + "localhost" +
							":" + "3306" + "/"+"webappdb?serverTimezone=UTC", connectionProps); //db서버 이름 써줌
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Connected to database");
		
		try {
			JDBCMain.viewTable(conn);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			JDBCMain.insertWithParam(conn, 400, "newone");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			JDBCMain.viewTable(conn);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public static void viewTable(Connection con) throws SQLException {
		Statement stmt = null;
		String query = "select id, password from user";
		
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String uID = rs.getString(1);
				String passwd = rs.getString(2);
				System.out.println(uID + "\t" + passwd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stmt != null) { stmt.close(); }
		}
	}
	
	public static void insertWithParam(Connection con, int newId, String newpasswd) 
		throws SQLException {
			PreparedStatement pstmt = null;
			
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement("INSERT INTO user VALUES( ?, ?)");
				pstmt.setInt(1, newId);
				pstmt.setString(2, newpasswd);
				pstmt.executeUpdate();
				con.commit();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {pstmt.close();}
			}
		}
}
