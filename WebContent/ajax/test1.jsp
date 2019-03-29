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

	function sendIt(){
		
		//XMLHttpRequest 생성
		//var xmlHttp = new ActiveXObject("Msxml2.XMLHTTP"); //IE5.0이후
		//var xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); //IE5.0이전
		//var xmlHttp = new XMLHttpRequest(); //non IE(InternetExplore)
	
		xmlHttp = createXMLHttpRequest();
	
		//먼저 자바스크립트로
		var query = "";
		var su1 = document.getElementById("su1").value;
		var oper = document.getElementById("oper").value;
		var su2 = document.getElementById("su2").value;
		
		query = "test1_ok.jsp?su1=" + su1 + "&su2=" + su2 + "&oper=" + oper;
		
		//onreadystatechange
		//서버가 작업을 마치고 클라이언트에게 정보를 돌려줄때(갔다가 작업하고 왔을때 *다시 돌려줘야함!!)
		//자동으로 실행할 매소드를 지정한다
		//이상없이 돌아오면 4가 반환되는데 onreadystatechang는 4를 받으면 실행할 매소드를 지정해준다
		
		xmlHttp.onreadystatechange = callback; //callback이라는 함수를 만들겠다(사용자 정의)

		
		//다시 돌려주기(oper라는 데이터를)
		xmlHttp.open("GET",query,true); // true : 비동기식 방식
		xmlHttp.send(null); //post방식일때만 보낼값을 같이 보내준다
		
		
	}
	
	function callback(){
		
		//4 : 다 보냈어용
		if(xmlHttp.readyState==4){
			//status : 정상(200) / 비정상(404)
			if(xmlHttp.status==200){

				//전문적인 친구에게 넘기기
				//why? 나중에 길어지기 때문에
				printData();
				
			}
			
		}
		
		
	}
	
	
	function printData(){
		
		
		var result = xmlHttp.responseXML.getElementsByTagName("root")[0];
		var out = document.getElementById("resultDIV");
		
		out.innerHTML = "";
		//none이라고 초기화 했던 거 지우기(값 보이게 하기_)
		out.style.display ="";
		
		out.innerHTML = result.firstChild.nodeValue;
		
		
	}
	
	
	
	
	

</script>

</head>
<body>

<input type="text" id="su1"/>
<select id="oper">
	<option value="hap">더하기</option>
	<option value="sub">뻬기</option>
	<option value="mul">곱하기</option>
	<option value="div">나누기</option>
</select>
<input type="text" id="su2"/>

<input type="button" value=" = " onclick="sendIt();"/>

<br/><br/>

<div id="resultDIV" style="display: none;">

</div>

</body>
</html>