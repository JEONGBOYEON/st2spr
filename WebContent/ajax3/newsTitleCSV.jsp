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

.news{
font-size:10pt;
display:block;
margin: 0 auto;
background: yellow;
color: blue;
border: 1px dached black;
width: 50%;
}

.newsError{
font-size:10pt;
display:block;
margin: 0 auto;
background: yellow;
color: red;
border: 1px dached black;
width: 50%;
}

</style>

<script type="text/javascript" src="<%=cp%>/data/js/httpRequest.js"></script>

<script type="text/javascript">

	function newsTitle(){
		
		
		//hideNewsDiv();
		
		sendRequest("newsTitleCSV_ok.jsp", null, displayNewsTitle, "GET");
		
		setTimeout(newsTitle, 3000); 
		
	}
	
	function displayNewsTitle(){
		
		if(httpRequest.readyState==4){
			
			if(httpRequest.status==200){
				
				var csvStr = httpRequest.responseText;
				
				//alert(csvStr);
				
				// |앞(csvArray[0]) = 데이터 갯수 
				// |뒤(csvArray[1]) = 데이터
				var csvArray = csvStr.split("|");

				// |앞(csvArray[0]) = 데이터 갯수 
				var countStr = csvArray[0];
				
				if(countStr==0){
					alert("News가 없다!");
					return;
				}

				// |뒤(csvArray[1]) = 데이터
				var csvData = csvArray[1];
				
				// 뉴스 하나하나를 *로 구별해서 배열에 저장
				var newsTitleArray = csvData.split("*");
				
				var html = "";
				
				//숫자생성
				html+="<ol>";
				
				//뉴스의 갯수만큼 반복
				for(var i=0;i<newsTitleArray.length;i++){
					
					var newsTitle = newsTitleArray[i];
					
					html += "<li>" + newsTitle + "</li>";
					
				}
					
				html += "</ol>";
				
				
				var newsDiv = document.getElementById("newsDiv");
				
				newsDiv.innerHTML = html;
				
			}else{
				var newsDiv = document.getElementById("newsDiv");
				newDiv.innerHTML = httpRequest.status + ":에러발생";
				
				//IE인 경우
				newsDiv.className = "newsError";
			
				//IE가 아닌 경우
				newsDiv.setAttrubute("class","newError");
				
				
			}
			
		}
		
	}
	
	function showNewsDiv(){
		var newsDiv = document.getElementById("newsDiv");
		newsDiv.style.display = "block"; //뉴스보임
	}

	function hideNewsDiv(){
		var newsDiv = document.getElementById("newsDiv");
		newsDiv.style.display = "none"; //뉴스안보임
	}
	
	window.onload = function(){
		newsTitle();
	}
	
</script>

</head>
<body>


<h1>헤드라인 뉴스</h1>
<hr/>
<br/>
<!-- 
<div onmouseover="showNewsDiv();" onmouseout="hideNewsDiv();"
style="display: block; border: 3px solid; width: 50%; margin: 0 auto;">
뉴스보기
</div>
 -->
<div style="display: block; border: 3px solid; width: 50%; margin: 0 auto;">
뉴스보기
</div>

<div id="newsDiv" class="news"></div>

<hr/>

</body>
</html>