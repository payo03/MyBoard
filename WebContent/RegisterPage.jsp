<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>	<!-- 회원가입 버튼 클릭시 RegisterServlet호출 -->
	<h4 align="center" >회원가입</h4>
		<form action="Register" method ="post">	<!-- required속성으로 인해 모든 항목 기입 -->
			<table border="1" align="center" width="50%">
				<tr>
	 				<td colspan="2"> <input type="text" name="name" placeholder="이름" required/> </td>
	 			</tr>
	 			<tr>
					<td colspan="2"> <input type="number" name="sid" placeholder="학번" required/> </td>
				</tr>
				<tr>
					<td colspan="2"> <input type="password" name="pw" placeholder="비밀번호" required/> </td>
				</tr>
				<tr>
					<td colspan="2"> <input type="number" name="grade" placeholder="학년" required/> </td>
				</tr>
				<tr>
					<td colspan="2"> <input type="text" name="subject" placeholder="학과" required/> </td>
				</tr>
	       		<tr>
					<td align="center"> <input type="submit" value="회원가입"/> </td>
	 				<td align="center"> <input type="reset" value="다시입력"/> </td>
	 			</tr> 	         	
 	        </table>
     	</form>
</body>
</html>