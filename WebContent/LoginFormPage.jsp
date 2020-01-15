<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = (String)session.getAttribute("memory");		//아이디 기억하기를 체크했다면 반환되는 값이 존재(로그아웃 했을때 사용)
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판</title>
</head>
<body>	<!-- LoginServlet을 호출하는 form -->
	<form action="${pageContext.request.contextPath}/Login" method="POST">
		<table border="1" align="center" width="50%">
			<tr>
				<td colspan="2" align="center"> <h4>게시판</h4> </td>
			</tr>
			<tr>	<!-- session영역을 통해 저장해놓았던 아이디 가져오기 -->
				<td align="center"> 학번 </td>
				<td align="center">
<%					if(id != null){ 	%>
						<input type="number" name="sid" value="<%= id %>" required>
<%					}else{ 	%>
						<input type="number" name="sid" required>
<%					}	 %>
				</td>
			</tr>
			<tr>
				<td align="center"> 비밀번호 </td>
				<td align="center"> <input type="password" name="pw" required> </td>
			</tr>
			<tr>
				<td colspan="2" align="center"> <input type="checkbox" name="memory" value="check">아이디 기억하기 </td>
			</tr>
			<tr>
				<td colspan="2" align="center"> <input type="submit" value="로그인"> </td>
			</tr>
			<tr>	<!-- 회원가입 jsp를 호출 -->
				<td colspan="2" align="center"> <a href="${pageContext.request.contextPath}/RegisterPage.jsp">회원가입</a> </td>
			</tr>		
		</table>
	</form>
</body>
</html>