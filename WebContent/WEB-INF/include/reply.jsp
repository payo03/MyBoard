<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="Dao.StudentDao"%>
<%@page import="Vo.Answer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="post" class="Vo.Post" scope="request" />
<jsp:useBean id="student" class="Vo.Student" scope="session" />
<%
	int manager = student.getManager();
	List<Answer> replyList = (ArrayList<Answer>)request.getAttribute("reply");
	Integer update = (Integer)request.getAttribute("update");

	Date nowTime = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/From/AnswerRegister" method="post">
	<div align="center">
		<input type="text" size="50" name="content" placeholder="댓글 추가"/>
		<input type="hidden" name="postNo" value="<%= post.getPostNo() %>" />
		<input type="submit" value="등록" />
	</div>
</form><br>
<table border="1" align="center" width="50%">
	<thead>
		<tr>
			<td> 작성자 </td>
			<td width="70%"> 내용 </td>
			<td width="20%"> 등록일자 </td>
		</tr>
	</thead>
	<tbody>
<%	for(int i=0; i<replyList.size(); i++) { %>
		<tr>
			<td> <%= StudentDao.getWriter(replyList.get(i).getSid()) %>	</td>
			<form action="${pageContext.request.contextPath }/From/AnswerChoice" method="post">

<%			if(update!=null) { %>
<%				if(replyList.get(i).getAnswerNo()==update) { %>
				<td>
					<input type="text" name="content" value="<%= replyList.get(i).getContent() %>" />
					<input type="hidden" name="answerNo" value="<%= replyList.get(i).getAnswerNo()%>" />
					<input type="hidden" name="postNo" value="<%= post.getPostNo() %>" /> <!-- viewPost를 호출하기위한 param -->
					<input type="submit" name="update" value="수정완료" />
					<input type="submit" name="delete" value="삭제" />
				</td>
<%				}else { %>
				<td> <%= replyList.get(i).getContent() %>
<%					if( replyList.get(i).getSid()==student.getSid() || manager==1) {%>
						<input type="hidden" name="answerNo" value="<%= replyList.get(i).getAnswerNo()%>" />
						<input type="hidden" name="postNo" value="<%= post.getPostNo() %>" /> <!-- viewPost를 호출하기위한 param -->
						<input type="submit" value="수정" />
						<input type="submit" name="delete" value="삭제" />
<%					} %>	
				</td>
<%				} %>
<%			}else { %>
				<td> <%= replyList.get(i).getContent() %>
<%					if( replyList.get(i).getSid()==student.getSid() || manager==1) {%>
						<input type="hidden" name="answerNo" value="<%= replyList.get(i).getAnswerNo()%>" />
						<input type="hidden" name="postNo" value="<%= post.getPostNo() %>" /> <!-- viewPost를 호출하기위한 param -->
						<input type="submit" value="수정" />
						<input type="submit" name="delete" value="삭제" />
<%					} %>	
				</td>
<%			} %>
			</form>
<%	
			Date reply_date = sdf.parse(replyList.get(i).getAnswerDate());
			
			long diff = nowTime.getTime() - reply_date.getTime();
			long sec = diff / 1000;
			long min = sec / 60;
			long hour = min / 60;
			long day = hour / 24;
			long month = day / 30;
			long year = month / 12;
%>	
			<td>
<%				
				if(year > 0) {
					out.println(year +"년 전");
				}else if(month > 0){
					out.println(month+"달 전");
				}else if(day > 0){
					out.println(day+"일 전");
				}else if(hour > 0) { 
					out.println(hour+"시간 전");
				}else if(min > 0) { 
					out.println(min+"분 전");
				}else { 
					out.println(sec+"초 전");
				} 
%>
			</td>
		</tr>
<%	} %>
	</tbody>
</table>
</body>
</html>