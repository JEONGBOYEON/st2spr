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

	//client시간
	function printClientTime(){
		var clientTimeSpan = document.getElementById("clientTimeSpan");
		
		var now = new Date();
		
		var timeStr = now.getFullYear() + "년" 
					+ (now.getMonth()+1)+ "월" 
					+ now.getDate() + "일" 
					+ now.getHours() + "시"
					+ now.getMinutes() + "분"
					+ now.getSeconds() + "초";
		
		clientTimeSpan.innerHTML = timeStr;
		
		//원하는초(1초)마다 자기자신(printClientTime)을 실행
		setTimeout("printClientTime()", 1000);
		
	}
	
	function requestTime(){
		
		XMLHttpRequest = createXMLHttpRequest();
		
		//넘겨줄 값을 없지만, 값을 넘겨줘야지 실행이 되기 때문에 필요없는 데이터 dummy를 보냄
		//dummy : 형식만 갖춰서 데이터를 넘겨준다
		//서버가 받아서 사용하지는 않는다
		XMLHttpRequest.open("GET","clock_ok.jsp?dummy=" + new Date().getDate(),true);
		
		XMLHttpRequest.onreadystatechange = printServerTime;
		
		XMLHttpRequest.send(null);
		
		setTimeout(requestTime, 1000);
		
	}
	
	
	function printServerTime(){
		
		if(XMLHttpRequest.readyState==4){
			if(XMLHttpRequest.status==200){
				
				var serverTimeSpan = document.getElementById("serverTimeSpan");
				
				serverTimeSpan.innerHTML = XMLHttpRequest.responseText;
			}else{
				//200이 아닌 경우
			}
		}else{
			//4가 아닌 경우
		}
		
	}
	
	window.onload = function(){
		printClientTime();//client의 시간(로컬 서버의 시간!)
		requestTime();//서버시간
	}

</script>

</head>
<body>

<h1>Clock</h1>

<br/><br/>

1.현재 클라이언트의 시간은 <span id="clientTimeSpan"></span>입니다.<br/>
2.현재 서버의 시간은 <span id="serverTimeSpan"></span>입니다.


</body>
</html>