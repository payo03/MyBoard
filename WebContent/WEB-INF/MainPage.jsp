<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="student" scope="session" class="Vo.Student" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>	<!-- top.jsp를 포함하는 메인페이지 -->
<jsp:include page="include/top.jsp" /><br>
<div align="center">
	<h3> 메인 페이지 입니다. </h3>
</div>
</body>
</html>