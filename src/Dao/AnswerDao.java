package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Connect.SQLConnection;
import Vo.Answer;

public class AnswerDao {
	
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	
	private static void close() {
		SQLConnection.close(rs);
		SQLConnection.close(pstmt);
		SQLConnection.close(conn);
	}

	public void register(Answer param) {
		String query = "INSERT INTO answer VALUES (?,?,?,SYSDATE(),null)";
		conn = SQLConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, param.getPostNo());
			pstmt.setInt(2, param.getSid());
			pstmt.setString(3, param.getContent());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}

	public List<Answer> get(int postNo) {
		String query ="SELECT * FROM answer WHERE POST_NO = ?";
		conn = SQLConnection.getConnection();
		List<Answer> reply = new ArrayList<Answer>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Answer param = new Answer();
				
				param.setPostNo(rs.getInt("POST_NO"));
				param.setSid(rs.getInt("SID"));
				param.setContent(rs.getString("CONTENT"));
				param.setAnswerDate(rs.getString("ANSWER_DATE"));
				param.setAnswerNo(rs.getInt("ANSWER_NO"));
				
				reply.add(param);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return reply;
	}

	public void delete(int answerNo) {
		String query = "DELETE FROM answer WHERE ANSWER_NO = ?";
		conn = SQLConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, answerNo);
			
			pstmt.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

}
