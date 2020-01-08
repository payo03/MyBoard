<%@ page import="Dao.StudentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="post" class="Vo.Post" scope="request" />
<jsp:useBean id="student" class="Vo.Student" scope="session" />
<%	//해당 post의 외래키(SID)를 통해 student의 NAME 받아오기
	String name = StudentDao.getWriter(post.getSid());
	int manager = student.getManager();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="include/top.jsp" /><br>
<table border="1" width="50%" align="center">	<!-- 제목, 내용, 작성일자를 자바빈즈 propert를 통해 출력 -->
	<tr align="center">
		<td width="20%"> 제목  </td>
		<td> <jsp:getProperty name="post" property="title" /> </td>
	</tr>
	<tr align="center">
		<td width="20%"> 내용  </td>
		<td style="height:350px"> <jsp:getProperty name="post" property="content" /> </td>
	</tr>
	<tr align="center">
		<td width="20%"> 작성일자 </td>
		<td> <jsp:getProperty name="post" property="postingDate" /> </td>
	</tr>
	<tr align="center">	<!-- 6번째 줄 getWriter함수를 통해 받아온 name값 출력 -->
		<td width="20%"> 작성자  </td>
		<td> <%= name %> </td>
	</tr>
</table><br>
<jsp:include page="include/reply.jsp" />
<div align="center">	<!-- 목록으로 돌아가는 PostListServlet호출 -->
	<a href="${pageContext.request.contextPath }/From/PostList"> 목록 </a>&nbsp
	<!-- 세션에 저장된 student의 name(로그인한 사용자)이 post의 name(작성자)과 같다면 수정,삭제항목 가능 -->
<%	if(student.getName().equals(name) || manager==1) { %>
		<!-- 해당하는 post의 postNo를 파라미터로 받아서 수정, 삭제 -->
		<a href="${pageContext.request.contextPath }/From/PostUpdating?PostNo=<%= post.getPostNo() %>"> 수정 </a>&nbsp
		<a href="${pageContext.request.contextPath }/From/PostDelete?PostNo=<%= post.getPostNo() %>" onClick="return confirm('정말 삭제하시겠습니까?');"> 삭제 </a>
<% 	} %>
</div>
</body>
</html>