<%@page import="Vo.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Student student = (Student)session.getAttribute("student");	//세션에서 꺼내와서 student객체에 담아준다
	String name = null;
	if(student.getManager()==1){
		name = "관리자";	//Manager일경우 StudentDAO의 Login메소드의 student세터를 통해 1의값 세팅
	}else {
		name = student.getName();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>	<!-- 메인페이지와 게시판으로 이동하는 Servlet 호출 -->
<div align="center">
	<a href="${pageContext.request.contextPath }/From/Main"> Main </a>&nbsp&nbsp&nbsp
	<a href="${pageContext.request.contextPath }/From/PostList"> 게시판 </a>
	<!-- 로그인된 student세션을 통해 name을 추출, Logout을 하는 Servlet호출 -->
	<div align="right">
		<b> <%= name %>님 안녕하세요</b>
		<a href="${pageContext.request.contextPath }/From/Logout">로그아웃</a>
	</div>
</div>
</body>
</html>