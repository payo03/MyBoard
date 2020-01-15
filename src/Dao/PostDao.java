package Dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Connect.SQLConnection;
import Vo.Post;
import Vo.Student;

public class PostDao {

	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	private static HttpSession session = null;
	//자원 반납하는 함수 호출
	private static void close() {
		SQLConnection.close(rs);
		SQLConnection.close(pstmt);
		SQLConnection.close(conn);
	}
	
	public PostDao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
	}
	
	public List<Post> select(Post param) {	//DB에 저장된 POST를 불러오기
		String query = null;
		Student student = (Student)session.getAttribute("student");
		
		if(student.getManager()==0) {
			if(param.getSid()==0) {
				query = "SELECT * FROM post WHERE AVAILABLE = 1 ORDER BY NOTICE desc, POST_NO desc";
			}else {
				query = "SELECT * FROM post WHERE AVAILABLE = 1 AND SID = ? ORDER BY NOTICE desc, POST_NO desc";
			}
		}else {
			if(param.getSid()==0) {
				query = "SELECT * FROM post ORDER BY NOTICE desc, POST_NO desc";
			}else {
				query = "SELECT * FROM post WHERE SID = ? ORDER BY NOTICE desc, POST_NO desc";
			}
		}
		List<Post> postList = new ArrayList<>();
		conn = SQLConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			if(param.getSid()!=0) {
				pstmt.setInt(1, param.getSid());
			}	//검색을 했을경우 sid를 파라미터로 받는다
			
			rs = pstmt.executeQuery();
			while(rs.next()) {	//rs는 POST 목록
				Post post = new Post();	//매번 새로운 객체를 List에 넣어줌
				
				post.setPostNo(rs.getInt("POST_NO"));
				post.setTitle(rs.getString("TITLE"));
				post.setSid(rs.getInt("SID"));
				post.setPostingDate(rs.getString("POSTING_DATE"));
				post.setContent(rs.getString("CONTENT"));
				post.setAvailable(rs.getInt("AVAILABLE"));
				post.setNotice(rs.getString("NOTICE"));
				post.setClick(rs.getInt("CLICK"));
				
				postList.add(post);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();	//자원 반납
		}
		return postList;	//POST 데이터를 가진 list객체 반환, 게시글이 없다면 null
	}

	public void register(Post param) {	//Post작성하는 함수
		String query = "INSERT INTO post VALUES(?,?,?,SYSDATE(),?,1,?,0)";	//MySQL문법의 SYSDATE()는 현재 시각, AVAILABLE=1, CLICK=0의 default
		conn = SQLConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, param.getPostNo());
			pstmt.setString(2, param.getTitle());
			pstmt.setInt(3, param.getSid());
			pstmt.setString(4, param.getContent());
			pstmt.setString(5, param.getNotice());
			
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

	public Post view(int postNo) {	//postNo를 parameter로 받아서 해당하는 post를 반환하는 함수e
		String update = "UPDATE post SET CLICK=(SELECT MAX(CLICK)+1) WHERE POST_NO=?";	//게시글 클릭시 조회수도 +1되야한다
		String query = "SELECT * FROM post WHERE POST_NO = ?";	//postNo에 해당하는 post SELECT
		conn = SQLConnection.getConnection();
		Post post = new Post();
		
		try {
			pstmt = conn.prepareStatement(update);
			pstmt.setInt(1, postNo);
			pstmt.executeUpdate();	//update 쿼리 실행
			
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
		String query = null;
		Student student = (Student)session.getAttribute("student");
		
		if(student.getManager()==0) {
			query = "UPDATE post set AVAILABLE = 0 WHERE POST_NO = ? and SID = ?";
		}else {
			query = "DELETE FROM post WHERE POST_NO=?";
		}
		conn = SQLConnection.getConnection();
		int confirm = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, param.getPostNo());
			if(student.getManager()==0) {		//작성자가 아닌사람이 URL로 삭제하려는 경우, student세션의 SID와 POST_NO가 일치하지 않는다
				pstmt.setInt(2, student.getSid());
			}
			
			confirm = pstmt.executeUpdate();	//위의 경우에는 confirm이 1을 반환하지 않는다.
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return confirm;
	}

	public Post getPost(Post param) {	//parameter로 넘겨받은 postNo와 sid에 해당하는 post객체 반환 함수
		String query = null;
		Student student = (Student)session.getAttribute("student");
		
		if(student.getManager()==0) {
			query = "SELECT * FROM post WHERE POST_NO = ? and SID = ?";	//PostNo와 SID에 parameter로 넘겨받은 값 세팅
		}else {
			query = "SELECT * FROM post WHERE POST_NO = ?"; 
		}
		conn=SQLConnection.getConnection();
		Post post = new Post();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, param.getPostNo());
			if(student.getManager()==0) {   //로그인 사용자의 student객체(<작성자-로그인 사용자>와구별하기 위해 받아온 객체)의 sid
				pstmt.setInt(2, student.getSid());
			}
			
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

	public List<Post> find(int sid) {
		List<Post> post = new ArrayList<>();
		conn = SQLConnection.getConnection();
		String query = "SELECT * FROM post WHERE SID = ? AND AVAILABLE = 1";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, sid);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Post param = new Post();
				
				param.setTitle(rs.getString("TITLE"));
				param.setSid(rs.getInt("SID"));
				param.setContent(rs.getString("CONTENT"));
				param.setPostingDate(rs.getString("POSTING_DATE"));
				param.setPostNo(rs.getInt("POST_NO"));
				param.setAvailable(rs.getInt("AVAILABLE"));
				param.setNotice(rs.getString("NOTICE"));
				
				post.add(param);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return post;
	}
	
}
