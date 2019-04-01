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

	var XMLHttpRequest;

	function ajaxRequestGet(){
		
		XMLHttpRequest = createXMLHttpRequest();
		
		var data = document.myForm.greeting.value;
		
		if(data==""){
			alert("데이터 입력!");
			document.myForm.greeting.focus();
			return;
		}
		
		//GET방식(한글깨짐)
		XMLHttpRequest.open("GET","ajaxGetPost_ok.jsp?greeting=" + data,true);
		XMLHttpRequest.onreadystatechange = viewMessage;
		XMLHttpRequest.send(null);
		
		
	}
	
	function viewMessage(){
		
		var divE = document.getElementById("printDIV");
		
		if(XMLHttpRequest.readyState==1|| //요청페이지 정보설정
				XMLHttpRequest.readyState==2|| //요청을 보내기 시작
				XMLHttpRequest.readyState==3){ //서버에서 응답오기 시작
			
			divE.innerHTML = "<image src='../image/processing.gif' width='50' height='50'/>";
			
		}else if(XMLHttpRequest.readyState==4){
			divE.innerHTML = XMLHttpRequest.responseText;	
		}else{
			divE.innerHTML = "<font color='red'>" + XMLHttpRequest.status + "에러 발생" + "</font>";
		}
		
	}
	
	function ajaxRequestPost(){

		XMLHttpRequest = createXMLHttpRequest();
		
		var data = document.myForm.greeting.value;
		
		if(data==""){
			alert("데이터 입력!");
			document.myForm.greeting.focus();
			return;
		}
		
		//GET방식(한글깨짐)
		XMLHttpRequest.open("POST","ajaxGetPost_ok.jsp",true);
		XMLHttpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XMLHttpRequest.onreadystatechange = viewMessage;
		XMLHttpRequest.send("greeting="+data);
	}

</script>

</head>
<body>

<h1>AjaxGetPost</h1>
<br/>
<form action="" name="myForm">
	<input type="text" name="greeting">
	<input type="button" value="전송" onclick="ajaxRequestGet();"> 
	<input type="button" value="전송" onclick="ajaxRequestPost();"> 
</form>

<br/><br/>

<div id="printDIV" style="border: 1px solid blue; width : 50%;"></div>


</body>
</html>