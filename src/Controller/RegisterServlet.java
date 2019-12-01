package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.StudentDao;
import Vo.Student;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter script = response.getWriter();
		
		//RegisterPage에서 받은 항목들 전부 불러오기
		int sid = Integer.parseInt(request.getParameter("sid"));
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		int grade = Integer.parseInt(request.getParameter("grade"));
		String subject = request.getParameter("subject");
		
		//Dao에 parameter로 넣어줄 Student객체 생성 후 값세팅
		Student param = new Student();
		param.setSid(sid);
		param.setName(name);
		param.setPassword(pw);
		param.setGrade(grade);
		param.setSubject(subject);
		
		StudentDao student = new StudentDao();
		
		//이미 가입된 학생이 있는지 SID를 통해 체크(PK)
		boolean confirm = student.check(sid);
		
		if(confirm) { //반환된게 없다면(parameter로 넘겨받은 SID에 해당하는 학생이 없다면) if절 실행
			student.register(param);	//학생을 등록
			
			script.println("<script>");
			script.println("alert(\"등록완료! 로그인해주세요\");");
			script.println("window.location = '" + request.getContextPath() + "/LoginFormPage.jsp'");
			script.println("</script>");
			
		}else {	//반환된게 있다면(parameter로 넘겨받은 SID에 해당하는 학생이 이미 존재한다면) else절 실행
			script.println("<script>");
			script.println("alert(\"동일 학번이 존재합니다!\");");
			script.println("history.go(-1)");
			script.println("</script>");
		}
	}

}
