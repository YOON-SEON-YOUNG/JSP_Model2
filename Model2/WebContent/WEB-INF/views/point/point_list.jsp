<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memo_point_list.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(function() {
	
});
</script>
</head>
<body>
<%-- ${pointList} --%>
<h1>포인트 내역</h1>
<hr>
<table border="1">
	<tr>
		<th>번호</th>
		<th>포인트 점수</th>
		<th>적립 날짜</th>
		<th>포인트 내역</th>
	<tr>
	</tr>
	<c:forEach items="${pointList}" var="pointVo" varStatus="status">
	<tr>
		<td>${status.count}</td>
		<td>${pointVo.m_point}</td>
		<td>${pointVo.give_date}</td>
		<td>${pointVo.point_desc}</td>
	</tr>
	</c:forEach>
</table>
<hr>
<a href="list.kh">돌아가기</a>
</body>
</html>