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

	div{
		margin: auto;
		border: 1px solid #0000ff;
		width:  600px;
		height: 70%;
		padding: 10px;
	}

</style>

<script type="text/javascript" src="<%=cp%>/data/js/httpRequest.js"></script>

<script type="text/javascript">
	
	function newsTitle(){
		
		sendRequest("newsTitleXML_ok.jsp", null, displayNewsTitle, "GET");
		setTimeout(newsTitle,1000);

	}
	
	function displayNewsTitle(){
		
		
		if(httpRequest.readyState==4){
			
			
			if(httpRequest.status==200){
				
				var doc = httpRequest.responseXML;
				
				var count = doc.getElementsByTagName("count")
	 					.item(0)
	 					.firstChild
	 					.nodeValue;
				
				
				if(count>0){
					
					var titleNL = doc.getElementsByTagName("title");
					
					var htmlData = "<ol>";
					
					for(var i=0;i<count;i++){
						htmlData += "<li>"
							+ titleNL.item(i).firstChild.nodeValue
							+ "</li>";
					}
					
					htmlData += "</ol>";
					
					var newsDiv = document.getElementById("news");
					
					newsDiv.innerHTML = htmlData;
					
				}
			}else{
				
				alert(httpRequest.status + ":" + httpRequest.statusText);
				
			}
		}
	}
	

	window.onload = function(){
		newsTitle();
	}
	
</script>

</head>
<body>

<div id="news"></div>

<input type="text"/>

</body>
</html>