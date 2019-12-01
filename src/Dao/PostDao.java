package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Connect.SQLConnection;
import Vo.Post;

public class PostDao {

	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	
	//자원 반납하는 함수 호출
	private static void close() {
		SQLConnection.close(rs);
		SQLConnection.close(pstmt);
		SQLConnection.close(conn);
	}

	public List<Post> select() {	//DB에 저장된 POST를 불러오기
		String query = "SELECT * FROM post WHERE AVAILABLE = 1 ORDER BY POST_NO desc";
		List<Post> post = new ArrayList<>();
		conn = SQLConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {	//rs는 POST 목록
				Post param = new Post();	//매번 새로운 객체를 List에 넣어줌
				
				param.setPostNo(rs.getInt("POST_NO"));
				param.setTitle(rs.getString("TITLE"));
				param.setSid(rs.getInt("SID"));
				param.setPostingDate(rs.getString("POSTING_DATE"));
				param.setContent(rs.getString("CONTENT"));
				param.setAvailable(rs.getInt("AVAILABLE"));
				
				post.add(param);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();	//자원 반납
		}
		return post;	//POST 데이터를 가진 list객체 반환, 게시글이 없다면 null
	}

	public void register(Post param) {	//Post작성하는 함수
		String query = "INSERT INTO post VALUES(?,?,?,SYSDATE(),?,1)";	//작성날짜는 SYSDATE()함수를 통해 작성하는 시각 받아오기
		conn = SQLConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, param.getPostNo());
			pstmt.setString(2, param.getTitle());
			pstmt.setInt(3, param.getSid());
			pstmt.setString(4, param.getContent());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}

	public int getPostNo() {	//PostNo를 안겹치게 해주는 함수
		String query = "SELECT MAX(POST_NO)+1 AS POST_NO FROM post";	//가장 마지막글의 PostNo + 1을 해주는 쿼리
		conn = SQLConnection.getConnection();
		int postNo = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			if(rs.first()) {	//글이 존재한다면 rs가 정상적으로 반환
				postNo = rs.getInt("POST_NO");
				if(postNo==0) {	//글이 존재하지않는다면 rs는 빈값을 반환
					postNo=1;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return postNo;
	}

	public Post view(int postNo) {	//postNo를 parameter로 받아서 해당하는 post를 반환하는 함수
		String query = "SELECT * FROM post WHERE POST_NO = ?";	//postNo에 해당하는 post SELECT
		conn = SQLConnection.getConnection();
		Post post = new Post();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, postNo);
			
			rs = pstmt.executeQuery();	//parameter로 받은 postNo에 해당하는 post데이터 rs에 반환
			if(rs.first()) {	//해당하는 rs가 존재하면 반환할 post객체에 세팅
				post.setPostNo(rs.getInt("POST_NO"));
				post.setTitle(rs.getString("TITLE"));
				post.setSid(rs.getInt("SID"));
				post.setPostingDate(rs.getString("POSTING_DATE"));
				post.setContent(rs.getString("CONTENT"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return post;	//post데이터에 해당하는 post객체 반환
	}

	public int delete(Post param) {
		String query = "UPDATE post set AVAILABLE = 0 WHERE POST_NO = ? and SID = ?";
		conn = SQLConnection.getConnection();
		int confirm = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, param.getPostNo());
			pstmt.setInt(2, param.getSid());
			
			confirm = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return confirm;
	}

	public Post getPost(Post param) {	//parameter로 넘겨받은 postNo와 sid에 해당하는 post객체 반환 함수
		String query = "SELECT * FROM post WHERE POST_NO = ? and SID = ?";	//PostNo와 SID에 parameter로 넘겨받은 값 세팅
		conn=SQLConnection.getConnection();
		Post post = new Post();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, param.getPostNo());
			pstmt.setInt(2, param.getSid());
			
			rs = pstmt.executeQuery();
			/*
			 * case) URL을 통해서 작성자가 아닌자가 수정할 페이지에 접속하는경우
			 * parameter로 넘겨받은 student객체의 sid가 post객체의 parameter로 세팅되어있으므로 POSTNO와 SID가 일치하지않으면 rs의 데이터는 없다
			 */
			if(rs.first()) {
				post.setPostNo(rs.getInt("POST_NO"));
				post.setTitle(rs.getString("TITLE"));
				post.setSid(rs.getInt("SID"));
				post.setPostingDate(rs.getString("POSTING_DATE"));
				post.setContent(rs.getString("CONTENT"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return post;	//데이터에 해당하는 post객체 반환
	}

	public void update(Post param) {	//parameter로 넘겨받은 post의 데이터를 통해 수정하는 함수
		String query = "UPDATE post set TITLE = ?, CONTENT = ?, POSTING_DATE = SYSDATE() WHERE POST_NO = ?";	//UPDATE쿼리 실행, DATE를 현재시각으로 수정
		conn = SQLConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, param.getTitle());
			pstmt.setString(2, param.getContent());
			pstmt.setInt(3, param.getPostNo());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
}
