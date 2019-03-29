<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="<%=cp%>/data/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">

	/* 
	//자바스크립의 내용
	
	//1. 일반변수 사용
	function welcome(){
		alert("환영");
	}
	
	var welcome = welcome();
	
	--------------------------
	
	var welcome = function(){
		alert("환영");
	}
	
	
	//2.내장변수 사용
	//위의 두가지 작업을 합쳐놓음
	
	window.onload = function(){
		alert("환영");
	}
	
	//위에꺼는 안나옴
	window.onload = function(){
		alert("진짜 환영");
	}
*/

	//jquery

	//javascript랑은 다르게 순서대로 모두 실행
	$(document).ready(function(){
		alert("환영");
	});

	//줄여서 사용할수 도 있다.
	$(function(){
		alert("진짜 환영");
	});

	$(document).ready(function(){
		alert("진짜 환영");
	});
	
	$(document).ready(function(){
		
		$(document.body).css("background","pink");
		
	});
	
	
	
	
</script>

</head>
<body>

</body>
</html>