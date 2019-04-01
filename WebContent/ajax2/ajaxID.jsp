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

<script type="text/javascript" src="<%=cp%>/data/js/ajaxUtil.js"></script>
<script type="text/javascript">

	var XMLHttpRequest = createXMLHttpRequest();
	
	function requestGet(){
		
		f = document.myForm;
		
		if(!f.userId.value){
			alert("아이디를 입력하세요!");
			f.userId.focus();
			return;
		}
		
		var params = "?userId=" + f.userId.value;
		
		XMLHttpRequest.open("GET","ajaxID_ok.jsp" + params,true);
		XMLHttpRequest.onreadystatechange = viewMessage;
		XMLHttpRequest.send(null);
		
	}
	
	function viewMessage(){
		
		if(XMLHttpRequest.readyState==4){
			if(XMLHttpRequest.status==200){
				
				var str = XMLHttpRequest.responseText;
				
				var divE = document.getElementById("resultDIV");
				divE.innerHTML =str;
				
			}
		}else{
			
			var divE = document.getElementById("resultDIV");
			divE.innerHTML = "<img width='15' height='15' src='../image/loading.gif'/>";
			
		}
	}

</script>

</head>
<body>

<h1>Ajax ID 확인</h1>
<br/>
<form action="" name="myForm">

<!-- //아이디를 입력하고 마우스를 키도를 딱 떼는 순간 검사 -->
아이디:<input type="text" name="userId" onkeyup="requestGet();"/>

</form>

<div id="resultDIV" style="color: red; border: 1px solid #000000; width: 40%"></div>

</body>
</html>