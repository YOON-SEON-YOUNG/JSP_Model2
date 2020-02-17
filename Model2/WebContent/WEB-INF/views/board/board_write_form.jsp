<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board_write_form.jsp</title>
<link rel="stylesheet" href="css/board.css"/>
</head>
<body>
<h1>글 쓰기 폼</h1>
<form action="write-pro.mem" method="post" enctype="multipart/form-data">
	<table border="1">
		<tr>
			<th><span class="red">*</span>제목</th>
			<td><input type="text" name="board_subject"
					maxlength="30" required/></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="board_content"
					cols="50" rows="10"></textarea></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td><input type="file" name="board_filename"/></td>
		</tr>
	</table>
	<input type="submit" value="작성완료"/>
</form>
</body>
</html>