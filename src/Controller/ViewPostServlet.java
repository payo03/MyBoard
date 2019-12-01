package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.PostDao;
import Vo.Post;

@WebServlet("/From/ViewPost")
public class ViewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PostList에서 제목을 눌렀을 때 해당하는 PostNo를 받아오기
		int postNo = Integer.parseInt(request.getParameter("PostNo"));
		
		PostDao post = new PostDao();
		Post result = new Post();
		//postNo를 통해서 해당하는 post를 받아오기
		result = post.view(postNo);

		if(result !=null) {	//post객체를 받아오면 해당하는 객체를 request영역에 저장
			request.setAttribute("post", result);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ViewPost.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
