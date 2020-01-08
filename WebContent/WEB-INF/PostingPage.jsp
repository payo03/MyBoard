<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="student" class="Vo.Student" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body><br>	<!-- 연결되는 서블릿은 PostRegister -->
<form action="${pageContext.request.contextPath }/From/PostRegister" method="post">
	<table border="1" align="center" width="50%">	<!-- required속성을 통해 목록 기입 필수 -->
		<tr>
			<td> <input type="text" name="title" placeholder="제목" style="width:95%" required/> </td>
		</tr>
		<tr>
			<td> 
				<textarea placeholder="내용을 입력" name="content" maxlength="2048" style="width:95%; height:350px" required></textarea> 
			</td>
		</tr>
		<tr>
			<td align="center">
<%			if(student.getManager()==1) { %>
				<input type="checkbox" name="notice" value="NOTICE" />공지글<br>
<% 			}	%>
				<input type="submit" value="작성" /> 
			</td>
		</tr>
	</table>
</form>
</body>
</html>