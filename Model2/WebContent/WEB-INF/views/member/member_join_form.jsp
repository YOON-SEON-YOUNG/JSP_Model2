<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member_join_form.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
var v = "${param.msg}";
if (v == "mem_join_fail")	{ // MemberJoinProService.java
	alert("회원 가입 실패");
} 
$(function () {
	var isCheckId = false;
	$("#btnCheckId").click(function () {
		var mem_id = $("input[name=mem_id]").val();
		var url = "member-check-id.ajax";
		var sendData = {
				"mem_id" : mem_id
		};
		$.get(url, sendData, function(receivedData) {
			var v = receivedData.trim();	// 공백제거
			console.log(receivedData);
			if ( v == "used_id" ){
				 $("#resultSpan").text("사용중인 아이디");
			} else if ( v == "available_id"){
				$("#resultSpan").text("사용 가능한 아이디");
				isCheckId = true;
			}
		});
	});
	
	$("#joinForm").submit(function () {
		var mem_pass = $("input[name=mem_pass]").val();
		var mem_pass2 = $("input[name=mem_pass2]").val();
		if (mem_pass != mem_pass2){
			alert("패스워드가 일치하지 않습니다.");
			return false;
		}
		
		if(isCheckId == false){
			alert("아이디 중복 체크를 해주세요.");
			return false;
		}
		
		var mem_id = $("input[name=mem_id]").val();	// h@@$&*
		console.log("----------------------");
		for (var v  = 0; v < mem_id.length; v++){
			var keyCode = mem_id.charCodeAt(v);		// 해당위치번째 문자의 코드값
			console.log(keyCode);
			
			if( !(97 <= keyCode && keyCode <= 122) &&	// 대,소문자가 아니고
					!(48 <= keyCode && keyCode <= 57)) {	// 숫자가 아니면
				alert("유효하지 않은 아이디\n영문자와 숫자 조합으로 입력");
				$("input[name=mem_id]").val("").focus();
// 				break; // for 종료
				return false; // for 종료, 폼 전송하지 않음
			}
		}
// 		return false;
	});
	
	$("input[name=mem_id]").keyup(function (e) {
		isCheckId = false;
		
	});
});
</script>
</head>
<body>
<h1>회원가입 폼</h1>
<form id="joinForm" action="member-join-pro.kh" method="post" enctype="multipart/form-data">
	<table border="1">
		<tr>
			<th>아이디</th>
			<td>
				<input type="text" name="mem_id" required/>
				<input type="button" value="중복확인" id="btnCheckId"/>
				<span id="resultSpan"></span>
			</td>
		</tr>
		<tr>
			<th>패스워드</th>
			<td><input type="password" name="mem_pass" required/></td>
		</tr>
		<tr>
			<th>패스워드 확인</th>
			<td><input type="password" name="mem_pass2" required/></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="mem_name" required/></td>
		</tr>
		<tr>
			<th>파일추가</th>
			<td><input type="file" name="mem_pic"/></td>
		</tr>
	</table>
	<input type="submit" value="가입완료"/>
</form>
</body>
</html>