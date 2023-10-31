package OracleConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleUtility {
	
	//getConnection()
	public static Connection getConnection() {
		Connection conn = null;
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String user = "iclass";
		String password = "0419";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 및 사용에 문제가 생겼습니다 : "+e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("driver 클래스를 찾을 수 없습니다 : "+e.getMessage());
		}
		
		return conn;
	}
	
	
	//close()
	public static void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
				System.out.println("! 연결 종료 !");
			} else System.out.println("Connection 이 null 입니다.");
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 종료 오류 : "+e.getMessage());
		}
	}
	
}//class end
