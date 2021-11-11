package Goals;
import java.sql.*;

public class GoalsHandler {
	
	public static void insertGoal(String service, String goal, int progress){
		String sql = "INSERT INTO Goals(Service, Goal, Progress) VALUES(?,?,?)";
		// Connect to the database
		GoalsDBConnection.connect();
		try {
			PreparedStatement pstmt = GoalsDBConnection.conn.prepareStatement(sql);
			pstmt.setString(1, service);
			pstmt.setString(2, goal);
			pstmt.setInt(3, progress);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Disconnect from the database
		GoalsDBConnection.closeConnect();
	}
	
	public static Object[][] retrieveGoals() {
		String sql = "SELECT * FROM Goals";
		Object[][] retrievedGoals = new Object[100][4];
		int i = 0;
		int j = 0;
		GoalsDBConnection.connect();
		try {
			PreparedStatement pstmt = GoalsDBConnection.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
		        String service = rs.getString("Service");
		        String goal = rs.getString("Goal");
		        String progress = (rs.getInt("Progress") + "%");
		        retrievedGoals[i][j] = service;
		        retrievedGoals[i][j+1] = goal;
		        retrievedGoals[i][j+2] = progress;
		        retrievedGoals[i][j+3] = false;
		        i++;
		    }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return retrievedGoals;
	}
	
	public static void deleteGoal(String service, String goal) {
		String sql = "DELETE FROM Goals WHERE Service = ? AND Goal = ?";
		try {
			PreparedStatement pstmt = GoalsDBConnection.conn.prepareStatement(sql);
			pstmt.setString(1, service);
			pstmt.setString(2, goal);
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void addGoal(String service, String goal, int progress) {
		String sql = "INSERT INTO Goals(Service, Goal, Progress) VALUES(?,?,?)";
		try {
			PreparedStatement pstmt = GoalsDBConnection.conn.prepareStatement(sql);
			pstmt.setString(1, service);
			pstmt.setString(2, goal);
			pstmt.setInt(3, progress);
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}	
}