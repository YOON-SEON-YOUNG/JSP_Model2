<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board_list.jsp</title>
<script>
	var v = "${sessionScope.msg}";
	if (v == "success")	{					// BoardDeleteProService.java
		alert("게시글 삭제 성공.");
	} else if (v == "fail")	{				// BoardDeleteProService.java
		alert("게시글 삭제 실패");
	} else if (v == "mem_join_success")	{	// MemberJoinProService.java
		alert("회원 가입 성공");
	} else if (v == "login_success")	{	// MemberLoginProService.java
		alert("로그인 성공");
	} else if (v == "login_fail") { 		// MemberLoginProService.java
		alert("로그인 실패");
	} else if (v == "not_login") {			// MemberFrontController.java
		alert("로그인 후 이용 가능합니다.")
	}
</script>
</head>
<body>
<c:remove var="msg" scope="session"/>	<!-- 세션지우기 -->
<%-- ${sessionScope.mem_id} 로그인 유저 정보--%>
<c:choose>
	<c:when test="${not empty mem_id}">
		${mem_id}님 반갑습니다.
		<a href="logout.mem">로그아웃</a> |
		<span>쪽지(<a href="memo-list.mem">${memoCount}</a>)</span>
		|
		<span>포인트(<a href="point-list.mem">${mem_point}</a>)</span>
	</c:when>
	<c:otherwise>
		<form action="member-login-pro.kh" method="post">
			<table border="1">
				<tr>
					<th>아이디</th>
					<td><input type="text" name="mem_id" required /> </td>
				</tr>
				<tr>
					<th>패스워드</th>
					<td><input type="password" name="mem_pass" required /> </td>
				</tr>
				<tr>
					<td><input type="submit" value="로그인"/> </td>
					<td><a href="member-join-form.kh">회원가입</a></td>
				</tr>
			</table>
		</form>
	</c:otherwise>
</c:choose>
<%-- ${param.msg} --%>
<h1>글 목록</h1>
<%-- ${list} --%>
<a href="write-form.mem">글쓰기</a>
<table border="1">
	<tr>
		<th>글번호</th>
		<th>이미지</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
		<th>IP</th>
	</tr>
	<!-- for (BoardVo boardVo : list) -->
	<c:forEach items="${list}" var="boardVo">
	<tr>
		<td>${boardVo.board_num}</td>
		<td>
		<c:choose>
			<c:when test="${empty boardVo.board_filename }">
				<img src="images/default.jpg" width="50"/>
			</c:when>
			<c:otherwise>
				<img src="upload/${boardVo.board_filename}" width="50"/>
			</c:otherwise>
		</c:choose>
		</td>
		<td><a href="content.kh?board_num=${boardVo.board_num}"
				title="${boardVo.board_content}">${boardVo.board_subject}	[${boardVo.reply_count}]</a></td>
		<td>${boardVo.board_writer}</td>
		<td>${boardVo.board_reg_date}</td>
		<td>${boardVo.board_read_count}</td>
		<td>${boardVo.board_ip}</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>