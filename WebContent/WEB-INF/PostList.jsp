<%@ page import="Dao.StudentDao"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Vo.Post"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<Post> postList = (ArrayList<Post>)request.getAttribute("PostList");	//request에 저장된 List를  받아오기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="include/top.jsp" /><br>	<!-- table영역에 List를 뿌려주기 -->
<table border="1" align="center" width="70%">
	<thead>
		<tr>
			<td width="15%"> 글 번호 </td>
			<td width="20%"> 제목 </td>
			<td width="20%"> 작성자 </td>
			<td width="25%"> 작성 날짜 </td>
		</tr>
	</thead>
	<tbody>	<!-- for문을 통해 해당하는 순번에 따라 배치 -->
<%		for(int i=0; i<postList.size(); i++) { %>
			<tr>
				<td> <%= postList.get(i).getPostNo() %> </td>
				<td width="20%"> <!-- 게시글을 확인할수 있도록 링크연결, 파라미터로 Post 순번을 넘겨줌 -->
					<a href="${pageContext.request.contextPath }/From/ViewPost?PostNo=<%= postList.get(i).getPostNo() %>"> <%= postList.get(i).getTitle() %> </a> 
				</td>	<!-- 작성한 SID를 외래키로 받아서 작성자를 보기 -->
				<td width="20%"> <%= StudentDao.getWriter(postList.get(i).getSid()) %> </td>
				<td width="25%"> <%= postList.get(i).getPostingDate() %> </td>
			</tr>
<%		} %>
	</tbody>
</table><br>
<div align="center">	<!-- 글 쓰기 링크 연결, PostingServlet호출 -->
	<a href="${pageContext.request.contextPath }/From/Posting" >글 작성하기</a>
</div>
</body>
</html>