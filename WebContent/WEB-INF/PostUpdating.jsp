<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="post" class="Vo.Post" scope="request" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body><br>	<!-- PostUpdate서블릿 호출, get방식으로 PostNo를 parameter로 받음. method가 post여도 Servlet에서 get으로 변경 -->
<form action="${pageContext.request.contextPath }/From/PostUpdate?PostNo=<%= post.getPostNo() %>" method="post">
	<table border="1" align="center" width="50%">	<!-- value값에 수정전의 post데이터를 넣어줌 -->
		<tr>
			<td> 
				<input type="text" name="title" value="<jsp:getProperty name="post" property="title" />" placeholder="제목" style="width:95%" required />
			</td>
		</tr>
		<tr>
			<td> 
				<textarea placeholder="내용을 입력" name="content" maxlength="2048" style="width:95%; height:350px" required><jsp:getProperty name="post" property="content" /></textarea>  
			</td>
		</tr>
		<tr>
			<td align="center"> <input type="submit" value="수정" /> </td>
		</tr>
	</table>
</form>
</body>
</html>