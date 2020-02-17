<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board_update_form.jsp</title>
<link rel="stylesheet" href="css/board.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(function () {
	$("#updateForm").submit(function() {
		// 새로 선택한 파일이 있는 경우에는 바이너리
		// 없는 경우에는 텍스트 데이터로 폼 전송하기.
		var new_filename = $("input[name=new_filename]").val();
		console.log("new_filename: " + new_filename);
		if (new_filename != "")	{
// 			console.log("선택 파일 있음");
			$(this).attr("enctype", "multipart/form-data");
			$("input[name=isBinary]").val("Y");
		}
	});
});
</script>
</head>
<body>
<%-- ${boardVo} --%>
<h1>수정 폼</h1>
<form id="updateForm" action="update-pro.mem" method="post" >
	<input type="hidden" name="board_num" 		value="${boardVo.board_num}"/>
	<input type="hidden" name="board_filename" 	value="${boardVo.board_filename}"/>
	<input type="hidden" name="isBinary" 		value="N"/>
	<input type="hidden" name="board_writer" 	value="${boardVo.board_writer}"/>
	<table border="1">
		<tr>
			<th><span class="red">*</span>제목</th>
			<td><input type="text" name="board_subject"
					value="${boardVo.board_subject}"
					maxlength="30" required/></td>
		</tr>
		<tr>
			<th><span class="red">*</span>작성자</th>
			<td>${boardVo.board_writer}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="board_content" cols="50" rows="10">
${boardVo.board_content}
				</textarea>
			</td>
		</tr>
		<tr>
			<c:if test="${not empty boardVo.board_filename}">
			<th>첨부된 파일</th>
			<td>
				<img src="upload/${boardVo.board_filename}" width="200"/>
			</td>
			</c:if>
		</tr>
		<tr>
			<th>새 첨부파일</th>
			<td><input type="file" name="new_filename"/></td>
		</tr>
	</table>
	<input type="submit" value="수정 완료"/>
</form>
</body>
</html>