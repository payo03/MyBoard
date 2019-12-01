package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLConnection {

	public static Connection getConnection() {	//getConnection호출시 매번 새로운 지역변수 생성
		Connection conn = null;	//Connection객체를 지역변수로 설정
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC","root","1234");
		}catch(Exception e) {
			e.printStackTrace();
		}	//드라이버 로드 및 DB연동
		
		return conn;	//지역변수로 설정된 conn객체 반환
	}
	
	//자원반환 함수 목록
	public static void close(Connection conn) {
		try {
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt) {
		try {
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {
		try {
			rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
