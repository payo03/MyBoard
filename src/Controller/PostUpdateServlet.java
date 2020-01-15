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

@WebServlet("/From/PostUpdate")
public class PostUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter script = response.getWriter();
		//postNo를 parameter로 받아와서 update구문을 실행할 post결정
		int postNo = Integer.parseInt(request.getParameter("PostNo"));
		//수정한 title과 content를 받아오기
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		//Dao에 넘겨줄 parameter값 세팅
		Post param = new Post();
		param.setPostNo(postNo);
		param.setTitle(title);
		param.setContent(content);
		
		PostDao post = new PostDao(request, response);
		
		if(title!=null) {	//URL을 통해서 다른 postNo를 세팅한 경우
			post.update(param);
			
			script.println("<script>");
			script.println("alert(\"수정 완료!\");");
			script.println("window.location = '" + request.getContextPath() + "/From/ViewPost?PostNo="+postNo+"'");	//수정 완료되면 해당 게시글을 다시 볼 수 있도록 서블릿 호출
			script.println("</script>");
		}else{	//postNo를 통해 접근하면 parameter는 null이 되기때문에 else절 실행
			script.println("<script>");
			script.println("alert(\"잘못된 접근입니다.\");");
			script.println("window.location = '" + request.getContextPath() + "/From/ViewPost?PostNo="+postNo+"'");
			script.println("</script>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
