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

<link rel="stylesheet" href="<%=cp%>/data/css/style.css" type="text/css">
<script type="text/javascript" src="<%=cp%>/data/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">

	$(function(){
		
		listPage(1);
		
	});
	
	//등록하기 버튼
	$(function(){
		
		$("#sendButton").click(function(){
			
			var params = "name=" + $("#name").val() +
				"&email=" + $("#email").val() +
				"&content=" + $("#content").val();
			
			
			$.ajax({
				
				type:"POST",
				//.action : struct2와 연동 (원래는 jsp만 연결)
				url:"<%=cp%>/iguest/created.action",
				data:params,
				success:function(args){
					
					$("#listData").html(args);
					
					
					//새로고침이 되는게 아니라서 입력창에 값이 남아있는데
					//그 값을 지워주기
					$("#name").val("");
					$("#email").val("");
					$("#content").val("");
					$("#name").focus();
				},
				beforeSend:showRequest,
				error:function(e){
					alert(e.responseText);
				}
				
			});
			
		});
		
	});
	
	//가기전 사용자가 검색했는지 검사
	function showRequest(){
		
		//javascript는 trim이라는 내장매소드가 없어서 만들어 썻지만
		//jQuery에는 있어서 따로 안 만들어두 됨
		var name = $.trim($("#name").val());
		var email = $.trim($("#email").val());
		var content = $.trim($("#content").val());
		
		if(!name){
			alert("\n이름을 입력하세요.");
			$("#name").focus();
			return false;
		}
		
		if(!email){
			alert("\n이메일을 입력하세요.");
			$("#email").focus();
			return false;
		}
		
		if(!content){
			alert("\n내용을 입력하세요.");
			$("#content").focus();
			return false;
		}
		
		if(content.length>200){
			alert("\n내용은 200자 까지만 입력하세요.");
			$("#content").focus();
			return false;
		}
		
		return true;
	}
	
	
	function listPage(page){
		
		var url = "<%=cp%>/iguest/list.action"
		
		$.post(url,{pageNum:page},function(args){
			
			$("#listData").html(args);
			
		});
		
		$("#listData").show(); //display:block의 역할 - 보여지는거(none의 반대)
		
	}

	function deleteData(num,page){

		var url = "<%=cp%>/iguest/deleted.action"
		$.post(url,{num:num,pageNum:page},function(args){
			
			$("#listData").html(args);
			
		});	
		
	}

</script>


</head>
<body>

<span id="listData" style="display: none;"></span>

<br/><br/>

<table width="600" border="2" cellpadding="0" cellspacing="0"
bordercolor="#e6d4a6" align="center">
<tr height="40">
	<td style="padding-left: 20px;">방명록</td>
</tr>
</table>

<br/><br/>

<table width="600" border="0" cellpadding="0" cellspacing="0" align="center">
<tr>
	<td width="600" colspan="4" height="3" bgcolor="#e6d4a6"></td>
</tr>

<tr>
	<td width="60" height="30" bgcolor="#eeeeee" align="center">
	작성자
	</td>
	<td width="240" height="30" style="padding-left: 10px;">
		<input type="text" id="name" size="35" maxlength="20" class="boxTF"/>
	</td>

	<td width="60" height="30" bgcolor="#eeeeee" align="center">
	이메일
	</td>	
	<td width="240" height="30" style="padding-left: 10px;">
		<input type="text" id="email" size="35" maxlength="50" class="boxTF"/>
	</td>	
</tr>

<tr>
	<td width="600" colspan="4" height="1" bgcolor="#e6d4a6"></td>
</tr>

<tr>
	<td width="60" height="30" bgcolor="#eeeeee" align="center">
	내용
	</td>
	<td width="540" colspan="3" style="padding-left: 10px;">
		<textarea rows="3" cols="84" class="boxTF"
		style="height: 50px;" id="content"></textarea>
	</td>
</tr>

<tr>
	<td width="600" colspan="4" height="1" bgcolor="#e6d4a6"></td>
</tr>

<tr>
	<td width="600" colspan="4" height="30" align="right" style="padding-left: 15px;">
		<input type="button" value="등록하기" class="btn2" id="sendButton"/>
	</td>
</tr>
</table>

<br/>



</body>
</html>