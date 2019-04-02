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

<script type="text/javascript" src="<%=cp%>/data/js/log.js"></script>
<script type="text/javascript" src="<%=cp%>/data/js/member.js"></script>

<script type="text/javascript">

	function memberClass(){
		
		//자바스크립트는 자체적으로 객체 생성할 수 있음
		//1.member.js없이 실행(메모리에 객체 생성)
		var object1 = {};
		object1.id = "suzi";
		object1.name = "배수지";
		
		log("log1: " + object1.id +  "," + object1.name);

		//2.member.js없이 실행(메모리에 객체 생성)
		var object2 = {};
		object2.id = "sdf";
		object2.name = "강강";
		
		log("log2: " + object2.id +  "," + object2.name);
		
		//3.Member 클래스 객체 생성 후 변수명으로 호출
		//member.js사용
		var member = new Member("sdf","천송이","강남");
		log("member: " + member.id +  "," + member.name + "," + member.addr);
		
		//4.setter로 초기화하고 변수로 호출
		member.setValue("hyolee","이효리","제주도");
		log("member2: " + member.id +  "," + member.name + "," + member.addr);
		
		//5.getter로 호출
		var memberInfo = member.getValue();
		lof("member.getValue():" + memberInfo);
	}
	
	window.onload = function(){
		memberClass();
	}

</script>

</head>
<body>

<h1>Javascript 클래스 사용</h1>
<hr/>

<div id="console"></div>


</body>
</html>