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

<style type="text/css">
	.suggest{
	display: none;
	position: absolute;
	left : 11px;
	top : 131px;
	}
</style>


<script type="text/javascript" src="<%=cp%>/data/js/httpRequest.js"></script>
<script type="text/javascript">

	function sendKeyword(){
		
		var userKeyword = document.myForm.userKeyword.value;
		
		if(userKeyword==""){
			hide();
			return;
		}
		
		var params = "userKeyword=" + userKeyword;
		
		sendRequest("suggestClient_ok.jsp",params,displaySuggest,"POST");
		
	}
	
	
	function displaySuggest(){
		if(httpRequest.readyState==4){
			if(httpRequest.status==200){
				
				var resultText = httpRequest.responseText;
				
				var resultArray = resultText.split("|");
				
				var count = parseInt(resultArray[0]);
				
				var keywordList = null;
				
				if(count>0){
					
					keywordList = resultArray[1].split(",");

					var html = "";
					
					for(var i=0; i<keywordList.length; i++){

						//select 호출 모든 ajax에 링크달아주기
						html += "<a href=\"javascript:select('" + 
								keywordList[i] + "');\">" + 
								keywordList[i] + "</a><br/>";
						//<a href = "javascript:select('ajax');>ajax</a><br/>"
						
					}
					
					var suggestListDiv = document.getElementById("suggestListDiv");
					
					suggestListDiv.innerHTML = html;
					
					show();
				}
				
			}else{
				//status!=200
				hide()
			}
		}else{
			//status!=4
			hide()
		}
	}
	
	
	//사용자가 제시어에서 클릭한 키워드
	function select(selectKeyword){
		//클릭한 제시어를 inputbox에 넣어줌
		document.myForm.userKeyword.value = selectKeyword;
		hide();
	}
	
	function show(){
		var suggestDiv = document.getElementById("suggestDiv");
		suggestDiv.style.display = "block";
	}
	
	function hide(){
		var suggestDiv = document.getElementById("suggestDiv");
		suggestDiv.style.display = "none";
	}
	
	
	
</script>

</head>
<body>

<h1>제시어</h1>
<hr/>
<br/>

<form action="" name="myForm">
<input type="text" name="userKeyword" onkeyup="sendKeyword();"/>
<input type="button" value="검색">

<div id="suggestDiv" class="suggest">
	<div id="suggestListDiv"></div>
</div>

</form>


</body>
</html>