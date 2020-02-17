<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board_delete_form.jsp</title>
</head>
<body>
${board_num}
<h1>삭제 폼</h1>
<form action="delete-pro.mem">
	<input type="hidden" name="board_num" value="${board_num}"/>
	<table border="1">
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="board_pass"/></td>
		</tr>
	</table>
	<input type="submit" value="삭제하기"/>
</form>
</body>
</html>