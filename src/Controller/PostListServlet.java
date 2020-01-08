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
import Dao.StudentDao;
import Vo.Post;

@WebServlet("/From/PostList")
public class PostListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StudentDao student = new StudentDao();
		
		String writer = request.getParameter("writer");
		
		Post param = new Post();
		if(writer!=null) {
			int sid = student.find(writer);
			param.setSid(sid);
		}
		
		PostDao post = new PostDao(request, response);
		List<Post> result = new ArrayList<>();

		result = post.select(param);
		
		if(result!=null) {	//게시글이 존재하면 해당하는 리스트를 request영역에 저장
			request.setAttribute("PostList", result);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/PostList.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
