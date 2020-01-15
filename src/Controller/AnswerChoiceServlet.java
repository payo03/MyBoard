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

		System.out.println(up);
		int postNo = Integer.parseInt(request.getParameter("postNo"));
		int answerNo = Integer.parseInt(request.getParameter("answerNo"));
		
		AnswerDao answer = new AnswerDao();
		Answer param = new Answer();
		param.setAnswerNo(answerNo);
		param.setContent(content);
		
		if(de!=null) {
			answer.delete(answerNo);
		}
		if(up!=null) {		//수정하였을 경우, null값을 세팅해주어서 다시 초기 게시글을 들어갔을때의 모습으로 변경
			request.setAttribute("update", null);
		}else {				//up가 null일경우, 초기 수정버튼을 누를시 해당 댓글의 번호를 update변수로 넘김
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
