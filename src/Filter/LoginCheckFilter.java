package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//로그인 학생 세션이 존재하는지 여부
@WebFilter("/From/*")
public class LoginCheckFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;	//다운 캐스팅
		HttpSession session = httpServletRequest.getSession();
		
		if(session.getAttribute("student")!=null) {	//로그인 세션 검사
			chain.doFilter(request, response);
		}else {	//세션 없다면 첫 화면으로 복귀
			RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginFormPage");
			dispatcher.forward(request, response);
		}
	}
}
