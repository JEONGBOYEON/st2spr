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

<%-- 
<link rel="stylesheet" type="text/css" href="<%=cp%>/data/css/jquery-ui.css"/>
<script type="text/javascript" src="<%=cp%>/data/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=cp%>/data/js/jquery-ui.js"></script>
 --%>
 
 <!-- 매번 링크걸기 귀찮아서 인터넷에 연결하기 -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
 
 <script type="text/javascript">

	$(function(){
		
		$("#dialog").hide();
		
		
		
		$("#btn1").click(function(){
			
			$("#dialog").dialog({
				height:240,
				width:300,
				modal:false
			})
			
		});
		
		

		$("#btn2").click(function(){
			
			$("#dialog").dialog({
				height:240,
				width:300,
				modal:true
			})
			
		});
		
		
		
		$("#btn3").click(function(){
			
			$("<div>").dialog({
				modal:true,
				open:function(){
					$(this).load("test4.jsp");			
				},
				height:400,
				width:400,
				title:"외부파일 띄우기"
			})
		
			
			
			
		});
		
		
		
		
	});

</script>

</head>
<body>

<div id="dialog" title="우편번호 검색">
	<p>동이름을 입력하세요</p>
	<p><input type="text"></p>
</div>

<div>
	<input type="button" value="모달리스" id="btn1"><br/>
	<input type="button" value="모달" id="btn2"><br/>
	<input type="button" value="외부파일" id="btn3"><br/>
</div>



</body>
</html>