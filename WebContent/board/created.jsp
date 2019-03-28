<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게 시 판</title>

<link rel="stylesheet" href="<%=cp %>/board/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp %>/board/css/created.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/board/js/util.js"></script>
<script type="text/javascript">

	function sendIt() {
		
		f = document.myForm;
		
		str = f.subject.value;
		str = str.trim();
		if(!str){
			alert("\n제목을 입력하세요.");
			f.subject.focus();
			return;
		}
		f.subject.value = str;
		
		str = f.name.value;
		str = str.trim();
		if(!str){
			alert("\n이름을 입력하세요.");
			f.name.focus();
			return;
		}
		f.name.value = str;
		
/* 		
		if(!isValidKorean(str)){
			alert("\n이름을 정확히 입력하세요.");
			f.name.focus();
			return;
		}
*/
		
		if(f.email.value){
			if(!isValidEmail(f.email.value)){
				alert("\n정상적인 E-Mail을 입력하세요.");
				f.email.focus();
				return;
			}
		}
		
		
		str = f.content.value;
		str = str.trim();
		if(!str){
			alert("\n내용을 입력하세요.");
			f.content.focus();
			return;
		}
		f.content.value = str;
		
		str = f.pwd.value;
		str = str.trim();
		if(!str){
			alert("\n패스워드를 입력하세요.");
			f.pwd.focus();
			return;
		}
		f.pwd.value = str;
		
		if(f.mode.value=="created"){
			f.action = "<%=cp%>/bbs/created.action";
		}else if(f.mode.value=="update"){
			f.action = "<%=cp%>/bbs/updated.action";
		}
		
		
		f.submit();
	}

</script>


</head>
<body>

<div id="bbs">
	<div id="bbs_title">
		게 시 판 ( Struts2 + Spring2.5 )
	</div>

	<form action="" name="myForm" method="post">
	<div id="bbsCreated">
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt >제&nbsp;&nbsp;&nbsp;&nbsp;목</dt>
				<dd>
					<input type="text" name="subject" value="${dto.subject }" size="74" maxlength="100" class="boxTF"/>
				</dd>
			</dl>
		</div>
		
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt >작성자</dt>
				<dd>
					<input type="text" name="name" value="${dto.name }" size="35" maxlength="20" class="boxTF"/>
				</dd>
			</dl>
		</div>
		
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt >E-Mail</dt>
				<dd>
					<input type="text" name="email" value="${dto.email }" size="35" maxlength="50" class="boxTF"/>
				</dd>
			</dl>
		</div>
		
		<div id="bbsCreated_content">
			<dl>
				<dt >내&nbsp;&nbsp;&nbsp;&nbsp;용</dt>
				<dd>
					<textarea rows="12" cols="63" name="content"  class="boxTA">${dto.content }</textarea>
				</dd>
			</dl>
		</div>
		
		<div class="bbsCreated_noLine">
			<dl>
				<dt >패스워드</dt>
				<dd>
					<input type="password" name="pwd" value="${dto.pwd }" size="35" maxlength="7" class="boxTF"/>
					&nbsp;(게시물 수정 및 삭제시 필요!!!)
				</dd>
			</dl>
		</div>
	</div>
	
	<div id="bbsCreated_footer">
		<input type="hidden" name="mode" value="${mode }"/>
		<input type="hidden" name="num" value="${num} ">
		<input type="hidden" name="pageNum" value="${pageNum} ">
		<input type="hidden" name="searchKey" value="${searchKey} ">
		<input type="hidden" name="searchValue" value="${searchValue} ">
		
	
		<input type="button" value="등록하기" class="btn2" onclick="sendIt();"/>
		<input type="reset" value="다시입력" class="btn2" onclick="document.myForm.subject.focus();"/>
		<input type="button" value="작성취소" class="btn2" onclick="javascript:location.href='<%=cp%>/bbs/list.action';"/>
	</div>
	
	</form>
	
</div>

</body>
</html>