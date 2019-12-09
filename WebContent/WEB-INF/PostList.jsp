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
			<td width="20%" align="center"> 글 번호 </td>
			<td width="30%" align="center"> 제목 </td>
			<td width="20%" align="center"> 작성자 </td>
			<td width="30%" align="center"> 작성 날짜 </td>
		</tr>
	</thead>
	<tbody>	<!-- for문을 통해 해당하는 순번에 따라 배치 -->
<%		for(int i=0; i<postList.size(); i++) { %>
			<tr>
<%				if(postList.get(i).getAvailable()==0) {	%>
				<td align="center" style="color:red"> <%= postList.get(i).getPostNo() %>
<%				}else { %>
<%					if(postList.get(i).getManager()==0) { %>
						<td align="center" style="color:green"> <%= postList.get(i).getPostNo() %>
<%					}else {	 %>
						<td align="center" style="color:orange"> <%= postList.get(i).getPostNo() %>
<%					} %>
<%				} %>
				</td>
				<td align="center"> <!-- 게시글을 확인할수 있도록 링크연결, 파라미터로 Post 순번을 넘겨줌 -->
					<a href="${pageContext.request.contextPath }/From/ViewPost?PostNo=<%= postList.get(i).getPostNo() %>"> <%= postList.get(i).getTitle() %> </a> 
				</td>	<!-- 작성한 SID를 외래키로 받아서 작성자를 보기 -->
				<td align="center"> <%= StudentDao.getWriter(postList.get(i).getSid()) %> </td>
				<td align="center"> <%= postList.get(i).getPostingDate() %> </td>
			</tr>
<%		} %>
	</tbody>
</table><br>
<form action="${pageContext.request.contextPath }/From/PostList" method="post">
	<div align="center">
		<input type="text" name="writer" placeholder="작성자" size="15"/> &nbsp
		<input type="submit" value="검색" />
	</div>
</form>
<div align="center">	<!-- 글 쓰기 링크 연결, PostingServlet호출 -->
	<a href="${pageContext.request.contextPath }/From/Posting" >글 작성하기</a>
</div>
</body>
</html>