package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.PostDao;
import Vo.Post;

@WebServlet("/From/PostDelete")
public class PostDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter script = response.getWriter();
		
		int postNo = Integer.parseInt(request.getParameter("PostNo"));
		
		Post param = new Post();
		PostDao post = new PostDao(request, response);
		
		param.setPostNo(postNo);
		
		int confirm = post.delete(param);
			
		if(confirm == 1) {
			script.println("<script>");
			script.println("alert(\"삭제 완료!\");");
				script.println("window.location = '" + request.getContextPath() + "/From/PostList'");
			script.println("</script>");
		}else {
			script.println("<script>");
			script.println("alert(\"잘못된 접근입니다.\");");
			script.println("history.go(-1)");
			script.println("</script>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
