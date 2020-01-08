package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.PostDao;
import Vo.Post;
import Vo.Student;

@WebServlet("/From/PostRegister")
public class PostRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter script = response.getWriter();
		// student세션을 통해서 로그인 하고있는 학생의 SID를 획득
		Student student = (Student) session.getAttribute("student");
		int sid = student.getSid();
		// 사용자가 입력한 제목, 내용을 받아오기
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String notice = request.getParameter("notice");
		// Dao를 통해 세팅할 PostNo값 불러오기
		PostDao post = new PostDao(request, response);
		int postNo = post.getPostNo();
		// Dao에게 parameter로 넘겨줄 Post객체 세팅
		Post param = new Post();
		param.setPostNo(postNo);
		param.setTitle(title);
		param.setContent(content);
		param.setSid(sid);
		param.setNotice(notice);

		try { // 작성한 Post를 Dao를 통해 등록
			post.register(param);

			script.println("<script>");
			script.println("alert(\"등록 완료!\");");
			script.println("window.location = '" + request.getContextPath() + "/From/PostList'"); // 서블릿 호출
			script.println("</script>");
		} catch (Exception e) {
			e.printStackTrace(); // register함수 오류시 로그 확인

			script.println("<script>");
			script.println("alert(\"오류발생!\");");
			script.println("window.location = '" + request.getContextPath() + "/From/PostList'");
			script.println("</script>");
		}
	}
}
