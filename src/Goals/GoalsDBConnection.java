package Goals;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class GoalsDBConnection {
  static Connection conn = null;
	
  public static void connect() {
		try {
			String url = "jdbc:sqlite:C:/Users/Alex/Documents/Java Programs/OnTrack 0.1/Goals Subsystem/GoalsDatabase.db";
			conn = DriverManager.getConnection(url);
			System.out.println("Connection Established to Goals Database");
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
  
	public static void closeConnect() {
		try {
			if (conn != null) {
			conn.close();
			System.out.println("Connection Closed");
			}
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
  
	public static void main(String args[]) {
		connect();
		closeConnect();
	}
}
