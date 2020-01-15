<%@ page import="Dao.StudentDao"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Vo.Post"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<Post> postList = (ArrayList<Post>)request.getAttribute("PostList");	//request로 저장함으로써 매번 새로운 List를  받아오기(검색기능)
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Manager은 삭제된 게시글을 확인가능, 공지글은 빨간색 표시에 밑줄 -->
<style>
	.delete {text-decoration: line-through;}
	.notice {color:red; text-decoration: underline;}
</style>
</head>
<body>
<jsp:include page="include/top.jsp" /><br>				<!-- table영역에 List를 뿌려주기 -->
<table border="1" align="center" width="70%">
	<thead>
		<tr>
			<td width="15%" align="center"> 글 번호 </td>
			<td width="30%" align="center"> 제목 </td>
			<td width="20%" align="center"> 작성자 </td>
			<td width="25%" align="center"> 작성/수정 날짜 </td>
			<td width="10%" align="center"> 조회수 </td>
		</tr>
	</thead>
	<tbody>	<!-- for문을 통해 해당하는 순번에 따라 배치 -->
<%		for(int i=0; i<postList.size(); i++) { %>
			<tr>
<%				if(postList.get(i).getAvailable()==0) {	%>	<!-- 삭제된 게시글일경우 style의 delete 적용 -->
				<td align="center" class="delete"> <%= postList.get(i).getPostNo() %>
<%				}else { %>
<%					if(postList.get(i).getNotice()==null) { %>	<!-- 공지글이 아닐경우 -->
						<td align="center"> <%= postList.get(i).getPostNo() %>
<%					}else {	 %>									<!-- 공지글일 경우 style의 notice 적용 -->
						<td align="center" class="notice"> <%= postList.get(i).getPostNo() %>
<%					} %>
<%				} %>
				</td>
				<td align="center"> <!-- 게시글을 확인할수 있도록 링크연결, 파라미터로 Post 순번을 넘겨줌 -->
					<a href="${pageContext.request.contextPath }/From/ViewPost?PostNo=<%= postList.get(i).getPostNo() %>"> <%= postList.get(i).getTitle() %> </a> 
				</td>	<!-- 작성한 SID를 외래키로 받아서 작성자를 보기 -->
				<td align="center"> <%= StudentDao.getWriter(postList.get(i).getSid()) %> </td>
				<td align="center"> <%= postList.get(i).getPostingDate().substring(0,11) + postList.get(i).getPostingDate().substring(11,13) +"시" + postList.get(i).getPostingDate().substring(14,16) +"분" %> </td>
				<td align="center"> <%= postList.get(i).getClick() %> </td>
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