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

import Dao.StudentDao;
import Vo.Student;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//LoginFormPage에서 입력받은 항목들 가져오기
		String sid = request.getParameter("sid");
		String pw = request.getParameter("pw");
		String rm = request.getParameter("memory");
		//StudentDao에 parameter로 넘겨줄 값들 세팅
		Student param = new Student();
		param.setSid(Integer.parseInt(sid));
		param.setPassword(pw);
		
		Student result = new Student();
		StudentDao student = new StudentDao();
		//parameter로 넘겨준 SID,PW에 해당하는 값을 조사
		result = student.login(param);
		if(result != null && result.getName()!=null) {	//SID와 PW에 해당하는 값이 존재한다면 if절 실행
			session.setAttribute("student", result);	//vo객체로 사용할 student객체를 session영역에 저장
			if(rm!=null) {	//아이디 기억하기를 통한 session영역 저장
				session.setAttribute("memory", sid);
			}else {
				session.removeAttribute("memory");
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/MainPage.jsp");
			dispatcher.forward(request, response);
		}else {	//SID와 PW에 해당하는 값이 없다면 else절 실행
			PrintWriter script = response.getWriter();
			
			script.println("<script>");
			script.println("alert(\"ID와 PW를 확인해주세요!\");");
			script.println("history.go(-1)");
			script.println("</script>");
		}
	}

}
