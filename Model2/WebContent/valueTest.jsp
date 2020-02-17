<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>valueTest.jsp</title>
<style>
.red {
	color: red;
}
.blue {
	color: blue;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(function() {
	$("#btn").click(function() {
		var v = $("#v1").val();		// 값을 읽어서
		$("#v2").text(v);			// v2에 텍스트 값으로 설정한다.
		$("#v3").attr("data-v", v);	// 속성 값 변화
		$(this).addClass(v);		// class="red"
	});
	
	$("#btn").mouseover(function() {
		console.log("mouseover");
		// v1의 글자를 blue로 설정하고
		$("#v1").val("blue");
		// 나머지 글자 색상을 파란색이 되도록
		$("#v2").addClass("blue" );
		$("#v3").addClass("blue" );
		$(this).addClass("blue" );
		
	});
	
	$("#btn").mouseout(function() {
		console.log("mouseout");
		// v1의 글자를 red로 설정하고
		$("#v1").val("red");
		// 나머지 글자 색상을 빨간색이 되도록
		$("#v2").removeClass("blue").addClass("red" );
		$("#v3").removeClass("blue").addClass("red" );
		$(this).removeClass("blue").addClass("red" );
		
	});
	// 이미지 위에 마우스 올리면 펭수
	$("#img").mouseover(function() {
		$("#img").attr("src", "images/pengsuClap.jpg");
	})
	
	
	// 마우스 내리면 엘사
	$("#img").mouseout(function() {
		$("#img").attr("src", "images/elsa.jpg");
	})
});
</script>
</head>
<body>
<img id="img" src="images/pengsuClap.jpg" width="80"/>


<hr>
<input type="text" id="v1"/>
<div id="v2">div</div>
<div><span id="v3">span</span></div>
<div><button id="btn">테스트</button></div>
</body>
</html>