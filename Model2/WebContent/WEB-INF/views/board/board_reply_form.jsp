<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board_reply_form.jsp</title>
</head>
<body>
<h1>답글 폼</h1>
<form action="reply-pro.mem" method="post" enctype="multipart/form-data">
	<input type="hidden" name="board_num" value="${board.board_num}"/>
	<table border="1">
		<tr>
			<th><span class="red">*</span>제목</th>
			<td><input type="text" name="board_subject" maxlength="30"
						value="ㄴ[Re:]${boardVo.board_subject}" required/> </td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="board_content" cols="50" rows="10">
			
-----------------------------
${boardVo.board_content}</textarea></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td><input type="file" name="board_filename"/></td>
		</tr>
	</table>
	<input type="submit" value="답글달기 완료"/>
</form>
</body>
</html>