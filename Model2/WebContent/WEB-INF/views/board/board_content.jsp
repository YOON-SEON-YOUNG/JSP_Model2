<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board_content.jsp</title>

<style>
#commentUpdateDiv {
	position: absolute;
	width: 300px;
	height: 100px;
	left: 200px;
	top: 600px;
	background-color: yellow;	
	z-index: 1000;
	display: none;
 	padding: 20px;
 	border: 1px solid red;
}

#heartDiv {
	margin-left: 50px;
	font-size: 50px;
}	

#heartDiv > span:first-child {
	cursor: pointer;
}

.gray{
	color: gray;
}

.red{
	color: red;
}
</style>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
var mem_id = "${sessionScope.mem_id}"; // getCommentList 여기에 쓸거임
var msg = "${param.msg}";
if (msg == "invalid_id") {
	alert("본인이 작성한 글만 가능합니다.");
}

$(document).ready(function() {
	var isLike = "";
	// 좋아요 요청
	$.get("is-like.ajax", {"board_num":"${boardVo.board_num}"}, function(rData) {
		var data = rData.trim();
		console.log(data);	// like / unlike
		if(data == "like"){
			$("#heartDiv").addClass("red");
		} else	{
			$("#heartDiv").addClass("gray");
		}
		isLike = data;	// like / unlike
	});
	
	// 좋아요 하트
	$("#heartDiv > span:first").click(function() {
		var url = "board-like.ajax";
		var sData = {
				"board_num"	: "${boardVo.board_num}"
		};
		$.get(url, sData, function(rData) {
			var data = rData.trim();
			console.log(data);
			if (data == "success"){
				var hasRed = $("#heartDiv").hasClass("red");
				if (hasRed == true){
					$("#heartDiv").removeClass("red").addClass("gray"); // 레드 하트 지우고 그레이 하트로
				} else {
					$("#heartDiv").removeClass("gray").addClass("red");	// 그레이 하트 지우고 레드 하트로
				}
				var likeCountElem = $("#heartDiv > span:last");
				var strCount = likeCountElem.text();
				var iCount = parseInt(strCount);
				if (isLike == "like"){
					// -1
					--iCount;
					isLike = "unlike";
				} else	{
					// +1
					++iCount;
					isLike = "like";
				}
				likeCountElem.text(iCount);
			}
		});
	});
	
	// 버튼 수정
	$("#btnUpdate").click(function() {
		location.href = "update-form.mem?board_num=${boardVo.board_num}";
	});
	
	// 버튼 삭제
	$("#btnDelete").click(function() {
		if (confirm("정말 삭제하시겠습니까?")){
			location.href = "delete-pro.mem?board_num=${boardVo.board_num}&board_writer=${boardVo.board_writer}";
		}
		
	});
	
	$("#btnReply").click(function() {
		location.href = "reply-form.mem?board_num=${boardVo.board_num}";
	});
	
	$("#btnList").click(function() {
		location.href = "list.kh";
	});
	
	$("#btnCommentInput").click(function() {
		var reply_text = $("textarea[name=reply_text]").val();
		var url = "comment-write.ajax";
		var sData = {
				"reply_text" : reply_text,
				"board_num"  : "${boardVo.board_num}"
			};	
		$.post(url, sData, function(rData) {
			console.log(rData);
			if (rData.trim() == "success"){
				getCommentList();
			}
		});
	});
	
	// 비동기 방식으로 생성된 엘리먼트에 대해서는 on을 쓴다.
	// on을 쓸때 주의할점!
	// 셀렉트하고자하는 엘리먼트의 상위 엘리먼트를 대상으로 사용한다.
	
	// 댓글 삭제 버튼
	$("#replyTable").on("click",".btnCommentDelete",function() {
// 		console.log('댓글 삭제 버튼 클릭됨');
		var that = $(this);
// 		console.log(that);
		var reply_num = that.parent().parent().find("td").eq(0).text();
		var board_num = "${boardVo.board_num}";
		console.log(reply_num);
		var url = "comment-delete.ajax";
		var sData = {
				"reply_num"	: reply_num,
				"board_num" : board_num
				
		};
		$.get(url, sData, function(rData) {
			var data = rData.trim();
			console.log(data);
			if (data == "success"){
				that.parent().parent().remove();	// 해당 <tr> 삭제
			}
		});
	});
	
	// 댓글 수정 버튼
	$("#replyTable").on("click",".btnCommentUpdate",function() {
// 		console.log('댓글 수정 버튼 클릭됨');
		var p = $(this).parent().prev();
		var reply_text = p.text();
		console.log(reply_text);
		
		var reply_num = $(this).parent().parent().find("td").eq(0).text();
		$("#commentUpdateDiv").attr("data-reply-num", reply_num);
		$("#commentUpdateDiv > textarea").val(reply_text);
		$("#commentUpdateDiv").show();		// 창 보이기
	});
	
	// 댓글 수정창 닫기 버튼
	$("#btnClose").click(function() {
		$("#commentUpdateDiv").hide(500);	// 창 감추기
	});
	
	// 댓글 수정 완료 버튼
	$("#btnFinsh").click(function() {
		var reply_num = $("#commentUpdateDiv").attr("data-reply-num");
		var reply_text = $("#commentUpdateDiv > textarea").val();
		console.log(reply_num);
		console.log(reply_text);
		
		var url ="comment-update.ajax"
		var sData = {
				"reply_num"	 : reply_num,
				"reply_text" : reply_text
		};
		$.get(url, sData, function(rData) {
			var data = rData.trim();
			if (data == "success"){
				getCommentList();
				$("#commentUpdateDiv").hide(500);
			}
		});
	});
	
	
// 	$("#btnComment").click(function() {
	function getCommentList() {
		
		var url = "board-comment-list.ajax";
		var sData = { 
				"board_num" : "${boardVo.board_num}"
			};
			$.getJSON(url, sData, function(rData) {
			console.log(rData);
			
			$("#replyTable > tbody").empty();	// replyTable 지우고~?
			$.each(rData, function() {
				var tr = "<tr>";
					tr += "<td>" + this.reply_num + "</td>";
					tr += "<td>" + this.replyer + "</td>";
					tr += "<td>" + this.reply_date + "</td>";
					tr += "<td>" + this.reply_text + "</td>";
					if (mem_id == this.replyer){
						tr += "<td><input type='button' value='수정' class='btnCommentUpdate'/></td>";
						tr += "<td><input type='button' value='삭제' class='btnCommentDelete'/></td>";
						tr += "</tr>";
					}
					
				$("#replyTable > tbody").append(tr);
			}); // $.each(rData, function()
		}); // $.getJSON(url, sData, function(rData)
	} // function getCommentList()
//	}); $("#btnComment")
	getCommentList();
});
			
</script>
</head>
<body>
<%-- ${boardVo} --%>
<div id="commentUpdateDiv">
	<textarea cols="40" rows="5"></textarea><br>
	<input type="button" value="수정완료" 	id="btnFinsh"/> 
	<input type="button" value="닫기" 		id="btnClose"/> 
</div>
<%-- ${boardVo} --%>
<h1 style="color:blue;">상세 보기</h1>
<table border="1" width="500" height="300">
	<tr>
		<th>글번호</th>
		<td>${boardVo.board_num}</td>
	</tr>
	<tr>
		<th>제목</th>
		<td>${boardVo.board_subject}</td>
	</tr>
	<tr>
		<th>내용</th>
		<td>${boardVo.board_content}</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>${boardVo.board_writer}</td>
	</tr>
	<tr>
		<th>IP</th>
		<td>${boardVo.board_ip}</td>
	</tr>
	<tr>
		<th>조회수</th>
		<td>${boardVo.board_read_count}</td>
	</tr>
	<tr>
		<th>작성일</th>
		<td>${boardVo.board_reg_date}</td>
	</tr>
	<tr>
		<th>이미지</th>
		<td>
		<c:choose>
		<c:when test="${empty boardVo.board_filename}">
			<img src="images/default.jpg" width="300"/>
		</c:when>
			<c:otherwise>
				<img src="upload/${boardVo.board_filename}" width="300"/>
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
</table>
<!-- 본인글일때만 수정, 삭제 보이도록 -->
<c:if test="${boardVo.board_writer == sessionScope.mem_id}">
<input type="button" value="수정" id="btnUpdate"/>
<input type="button" value="삭제" id="btnDelete"/>
</c:if> 
<input type="button" value="답글" id="btnReply"/>
<input type="button" value="목록" id="btnList"/>
<hr/>

<div id="heartDiv"><span>♥</span>	<span>${boardVo.like_count}</span></div>

<!-- <input type="button" value="댓글보기" id="btnComment"/> -->
<c:if test="${not empty sessionScope.mem_id}">	<!-- 로그인해야 덧글창 보이도록 -->
	<hr/>
	<textarea rows="3" cols="50" name="reply_text"></textarea><br>
	<input type="button" value="입력" id="btnCommentInput"/>
	<hr/>
</c:if>
<table id="replyTable">
<tbody></tbody>
</table>

</body>
</html>