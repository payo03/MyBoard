package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.PostDao;
import Vo.Post;
import Vo.Student;

@WebServlet("/From/PostUpdating")
public class PostUpdatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//로그인 사용자의 student객체(<작성자-로그인 사용자>와구별하기 위해 받아온 객체), 수정할 postNo 받아오기
		Student student = (Student) session.getAttribute("student");
		
		int sid = student.getSid();
		int postNo = Integer.parseInt(request.getParameter("PostNo"));
		int manager = student.getManager();
		//Dao에 parameter로 넘겨줄 post데이터 세팅
		Post param = new Post();
		if(manager==0) {
			param.setSid(sid);
			param.setPostNo(postNo);
		}else {
			param.setPostNo(postNo);
			param.setManager(manager);
		}
		
		Post result = new Post();
		PostDao post = new PostDao();
		//parameter로 넘겨받은 post데이터를 PostDao를 통해 받아오기
		result = post.getPost(param);
		
		if(result.getTitle()!=null) {	//rs가 정상적인 값을 반환했다면 if절 실행
			request.setAttribute("post", result);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/PostUpdating.jsp");
			dispatcher.forward(request, response);
		}else {	//rs의 데이터가 없다면, URL로 작성자가 아닌자가 수정하려 했다면
			PrintWriter script = response.getWriter();
			
			script.println("<script>");
			script.println("alert(\"잘못된 접근입니다!\");");
			script.println("history.go(-1)");
			script.println("</script>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
