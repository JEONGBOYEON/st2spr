<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp=request.getContextPath(); 
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="<%=cp%>/data/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">

	$(function(){
		
		$("#sendButton").click(function(){
			
			var params = "subject=" + $("#subject").val() + "&content=" + $("#content").val();
			
			$.ajax({
				
				type:"GET",
				url:"test2_ok.jsp",
				data:params,
				dataType:"xml",
				success:function(args){
					
					//xml에서 데이터 읽기
					$(args).find("status").each(function(){		//each는 반복문
						alert($(this).text());						
					});
					
					var str = "";
					
					$(args).find("record").each(function(){
						
						var id = $(this).attr("id");  // 숫자 1
						var subject = $(this).find("subject").text();
						var content = $(this).find("content").text();
						
						str += "id=" + id + ",subject=" + subject + 
						",content=" + content + "<br/>";
						
					});
					
					$("#resultDIV").html(str);				
				},
				beforeSend:showRequest,
				error:function(e){
					alert(e.responseText);	
				}
								
			});
			
		});
		
	});
	
	
	function showRequest() {
		
		var flag = true;
		
		if(!$("#subject").val()){
			alert("제목을 입력하세요");
			$("#subject").focus();
			return false;
		}
		
		if(!$("#content").val()){
			alert("내용을 입력하세요");
			$("#content").focus();
			return false;
		}
		
		return flag;
		
	}
	
	

</script>


</head>
<body>


제목:<input type="text" id="subject"/><br/>
내용:<input type="text" id="content"/><br/>

<input type="button" id="sendButton" value="보내기"/>
<br/><br/><br/>

<div id="resultDIV"></div>


</body>
</html>