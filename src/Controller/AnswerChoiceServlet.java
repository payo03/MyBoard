package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.AnswerDao;

@WebServlet("/From/AnswerChoice")
public class AnswerChoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String up = request.getParameter("update");
		String de = request.getParameter("delete");
		
		int postNo = Integer.parseInt(request.getParameter("postNo"));
		int answerNo = Integer.parseInt(request.getParameter("answerNo"));
		
		AnswerDao answer = new AnswerDao();
		if(de!=null) {
			answer.delete(answerNo);
		}else {
			request.setAttribute("update", answerNo);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/From/ViewPost?PostNo="+postNo);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
