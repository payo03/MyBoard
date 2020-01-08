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
			Calendar nowCal = Calendar.getInstance();
			int year = nowCal.get(Calendar.YEAR);
			int month = nowCal.get(Calendar.MONTH);
			int day = nowCal.get(Calendar.DATE);
			int hour = nowCal.get(Calendar.HOUR);
			int min = nowCal.get(Calendar.MINUTE);
			int sec = nowCal.get(Calendar.SECOND);
			
			int reply_year = Integer.parseInt(replyList.get(i).getAnswerDate().substring(0, 4));
			int reply_month = Integer.parseInt(replyList.get(i).getAnswerDate().substring(5, 7));
			int reply_day = Integer.parseInt(replyList.get(i).getAnswerDate().substring(8, 10));
			int reply_hour = Integer.parseInt(replyList.get(i).getAnswerDate().substring(11,13));
			int reply_min = Integer.parseInt(replyList.get(i).getAnswerDate().substring(14,16));
			int reply_sec = Integer.parseInt(replyList.get(i).getAnswerDate().substring(17,19));
%>	
			<td>
			
			</td>
		</tr>
<%	} %>
	</tbody>
</table>
</body>
</html>