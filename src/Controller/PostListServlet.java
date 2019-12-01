package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.PostDao;
import Vo.Post;

@WebServlet("/From/PostList")
public class PostListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PostList를 가져오기 위한 세팅
		PostDao post = new PostDao();
		List<Post> postList = new ArrayList<>();
		//List형식으로 select해서 반환받기
		postList = post.select();
		
		if(postList!=null) {	//게시글이 존재하면 해당하는 리스트를 request영역에 저장
			request.setAttribute("PostList", postList);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/PostList.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
