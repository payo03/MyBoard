package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connect.SQLConnection;
import Vo.Student;

public class StudentDao {
	
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	
	private static void close() {
		SQLConnection.close(rs);
		SQLConnection.close(pstmt);
		SQLConnection.close(conn);
	}
	
	public boolean check(int sid) {	//PK는 중복x, 이미 등록된 학생인지 확인하는 함수
		String query = "SELECT * FROM student WHERE SID = ?";	//parameter로 넘겨받은 SID를 확인
		conn = SQLConnection.getConnection();	//getConnection으로 해당하는 자원 획득
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sid);
			rs = pstmt.executeQuery();
			
			if(rs.first()) {
				return false;	//반환된게 있으면 false
			}
		} catch (SQLException e) {
			e.printStackTrace();	//DB연결 오류시 실행
		}finally {
			close();
		}
		return true;	//반환된게 없다면 true
	}
	
	public void register(Student param) {	//학생을 등록하는 함수
		String query = "INSERT INTO student VALUES (?,?,?,?,?)";	//INSERT문 실행
		conn = SQLConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, param.getSid());
			pstmt.setString(2, param.getName());
			pstmt.setString(3, param.getPassword());
			pstmt.setInt(4, param.getGrade());
			pstmt.setString(5, param.getSubject());
			
			pstmt.executeUpdate();	//등록완료
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	public Student login(Student param) {	//Parameter로 SID와 PW를 넘겨받아서 해당 데이터가 있는지 조사하는 함수
		String query = "SELECT * FROM student WHERE SID = ? and PASSWORD = ?";
		conn = SQLConnection.getConnection();
		Student student = new Student();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, param.getSid());
			pstmt.setString(2, param.getPassword());
			
			rs = pstmt.executeQuery();
			
			if(rs.first()) {	//SID와 PW에 해당하는 값이 있으면 반환될 student의 값 세팅
				student.setSid(rs.getInt("SID"));
				student.setPassword(rs.getString("PASSWORD"));
				student.setName(rs.getString("NAME"));
				student.setGrade(rs.getInt("GRADE"));
				student.setSubject(rs.getString("SUBJECT"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return student;	//rs가 반환되면 값을 가진 student, 없다면 null를 가진 student
	}

	public static String getWriter(int sid) {	//static을 통해서 StudentDao객체 생성하지않고 바로 접근, SID에 해당하는 이름 반환
		String query = "SELECT NAME FROM student WHERE SID = ?";	//파라미터로 넘겨받은 Post의 외래키(SID) 사용, SID에 해당하는 NAME SELECT
		conn = SQLConnection.getConnection();
		String name = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, sid);
			
			rs = pstmt.executeQuery();
			if(rs.first()) {	//SID에 해당하는 NAME값 rs에 저장
				name = rs.getString("NAME");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return name;	//NAME값 반환
	}
}
