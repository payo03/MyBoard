package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.AnswerDao;
import Vo.Answer;
import Vo.Student;

@WebServlet("/From/AnswerRegister")
public class AnswerRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Student student = (Student)session.getAttribute("student");
		int sid = student.getSid();
		int postNo = Integer.parseInt(request.getParameter("postNo"));
		String content = request.getParameter("content");
		
		Answer param = new Answer();
		param.setSid(sid);
		param.setPostNo(postNo);
		param.setContent(content);
		
		AnswerDao answer = new AnswerDao();
		answer.register(param);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/From/ViewPost?PostNo="+postNo);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
