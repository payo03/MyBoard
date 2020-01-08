package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.AnswerDao;
import Vo.Answer;

@WebServlet("/From/AnswerChoice")
public class AnswerChoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("content");
		String up = request.getParameter("update");
		String de = request.getParameter("delete");
		
		int postNo = Integer.parseInt(request.getParameter("postNo"));
		int answerNo = Integer.parseInt(request.getParameter("answerNo"));
		
		AnswerDao answer = new AnswerDao();
		Answer param = new Answer();
		param.setAnswerNo(answerNo);
		param.setContent(content);
		
		if(de!=null) {
			answer.delete(answerNo);
		}
		if(up!=null) {
			request.setAttribute("update", null);
		}else {
			request.setAttribute("update", answerNo);
		}
		
		if(content!=null) {
			answer.update(param);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/From/ViewPost?PostNo="+postNo);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
