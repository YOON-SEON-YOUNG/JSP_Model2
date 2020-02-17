<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.read {
	color : black;
}
.not_read {
	color : red;
}

/* 쪽지 읽기 창 사이즈 */
#memo_read_div {
	position: absolute;
	width: 200px;
	height: 80px;
	left: 600px;
	top: 200px;
	background-color: yellow;
	border: 1px dashed blue;
	z-index: 1000;
 	display: none; 
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(function() {
	
	// 쪽지 보내기 창
	$("#btnDiv > input").click(function() {
		$("#formDiv").slideDown(500);	// 500 millisecond (0.5초)
		$("#btnDiv").slideUp(500);
	}); // #btnDiv > input
	
	
	// 쪽지 읽기 창 열기
	$(".not_read_memo_text").click(function(e) {
// 		console.log(e);		// 이벤트 기능 볼 수 있음
		e.preventDefault();	// 브라우저의 기본기능 막기
		var that = $(this);
		var memo_num = $(this).attr("data-memo-num");
		var url = "memo-read.ajax";
		var sData = {
				"memo_num" : memo_num
		};	
		$.getJSON(url, sData, function(rData) {
			console.log(rData);
			var memo_open_date = rData.memo_open_date;
			var point = rData.point;
			var memo_text = that.text();
			
			var elemOpenDate = that.parent().parent().find("td").last();
// 			console.log(elemOpenDate);
			var elemSpan = $("#memo_read_div span");
// 			console.log(elemSpan);
			var elemMemoText = $("#memo_read_div td:first");
// 			console.log(elemMemoText);
			
			elemOpenDate.text(memo_open_date);
			elemSpan.text(point);
			elemMemoText.text(memo_text);
			
			$("#memo_read_div").fadeIn(500); // 서서히 나타나게함
			
			// 읽으면 색상변경~
			that.parent().parent().removeClass("not_read").addClass("read");
		});
	}); // not_read_memo_text
	
	
	// 쪽지 읽기 창 닫기
	$("#btn_close").click(function() {
		$("#memo_read_div").fadeOut(500);
	}); // btn_close
	
	
	// 쪽지 체크
	$("#chkAll").change(function() {
		console.log("change");
		var isChecked = $(this).is(":checked")	// 체크상태 
		var chkMemos = $(".chkMemo")	
// 		console.log($(this).is(":checked"));
		
		if (isChecked == true){
			chkMemos.prop("checked", true);		// 체크가 되어있지않다면 체크
		} else {
			chkMemos.prop("checked", false);	// 체크가 되어있다면 해지
		}
	}); // 체크
	
	
	// 선택한 쪽지 삭제
	$("#btnMemoDelete").click(function() {
		
		var chkMemos = $(".chkMemo");
// 		console.log(chkMemos);
		var memo_nums = "";
		var arrCheckedIndex  = [];
		var i = 0;
		$.each(chkMemos, function(index) {
			
			var isChecked = $(this).is(":checked");
			if(isChecked == true){
				memo_nums += $(this).attr("data-memo-num") + ",";
				arrCheckedIndex[i] = index;
				i++;
			}
		});
// 		console.log(memo_nums);
// 		console.log(arrCheckedIndex);
		var v = memo_nums.substring(0, memo_nums.length-1);
// 		console.log(v);
		var url = "memo-delete.ajax";
		var sData = {
				"memo_nums"	: v			// send_data
		};
		
		$.post(url, sData, function(rData) {
// 				console.log(rData);
				if (rData.trim() == "success")	{
					for (var arrI = 0; arrI < arrCheckedIndex.length; arrI ++){
						chkMemos.eq(arrCheckedIndex[arrI]).parent().parent().remove();
					//	chckbox								td		tr		dom에서 제거
					// DOM(Document Object Model)
					}
					for (var a = 0; a < $(".numbering").length; a++){
						$(".numbering").eq(a).text(a + 1);
						// eq: 0번째와 같은..equri (index와 같은...)
					}
				} // if
		});
	});	// 선택한 쪽지 삭제
	
});
</script>
</head>
<body>
<%-- ${memoList } --%>
<!-- 쪽지 읽기 창 -->
<div id="memo_read_div">
	<table>
		<tr>
			<td style="font-weight: bold;">쪽지 내용</td>
		</tr>
		<tr>
			<td style="font-size: 12px"><span>0</span> 포인트 획득</td>
		</tr>
		<tr>
			<td><input type="button" id="btn_close" value="닫기"></td>
		</tr>
	</table>
</div>
<h1>쪽지함</h1>
<hr>
<!-- 쪽지 보내기 -->
<div id="btnDiv">
	<input type="button" value="쪽지 보내기" />
</div>
	<input type="button" value="선택한 쪽지 삭제" id="btnMemoDelete"/>
	
<div id="formDiv" style="display: none;">
<form action="memo-send.mem" method="post">
	<table border="1">
		<tr>
			<td>받는이</td>
			<td><input type="text" name="memo_receiver"/></td>
		</tr>
		<tr>
			<td colspan="2"><textarea name="memo_text"
										rows="5" cols="30"></textarea></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="보내기"/></td>
		</tr>
	</table>
</form>
</div>
<hr>
<br>
<table border="1">
	<tr>
		<th><input type="checkbox" id="chkAll"></th>
		<th>번호</th>
		<th>쪽지 내용</th>
		<th>보낸이</th>
		<th>보낸 날짜</th>
		<th>읽은 날짜</th>
	</tr>
	<c:forEach items="${memoList}" var="memoVo" varStatus="status">
		<tr 
			<c:choose>
				<c:when test="${empty memoVo.memo_open_date}">
					class="not_read"
				</c:when>
				<c:otherwise>
					class="read"
				</c:otherwise>
			</c:choose>
		>
			<td><input type="checkbox" class="chkMemo"
						data-memo-num="${memoVo.memo_num}" ></td> <%-- 시퀀스에 저장된 실제 넘값 --%>
			<td class = "numbering">${status.count}</td>				<%--위에서 부터 차례로 번호 보이도록 1, 2, 3--%>
<%-- 			<td>${memoVo.memo_num}</td> --%>	<%--실제 글 번호 3, 2, 1--%>
			<td><a data-memo-num="${memoVo.memo_num}" 
					href="#" class="not_read_memo_text">${memoVo.memo_text}</a></td>
			<td>${memoVo.memo_sender}</td>
			<td>${memoVo.memo_send_date}</td>
			<td>
			<c:choose>
				<c:when test="${empty memoVo.memo_open_date}">
					읽지 않음 
				</c:when>
				<c:otherwise>
					${memoVo.memo_open_date}
				</c:otherwise>
			</c:choose>
			
			</td>
		</tr>
	</c:forEach>
</table>


<a href="list.kh">돌아가기</a>
</body>
</html>