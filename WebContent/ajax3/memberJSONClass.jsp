<%@ page contentType="text/plain; charset=UTF-8"%>
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

<script type="text/javascript" src="<%=cp%>/data/js/log.js"></script>
<script type="text/javascript" src="<%=cp%>/data/js/member_json.js"></script>

<script type="text/javascript">

	function memberClass(){
		
		var member = new com.util.Member("suzi","수지","서울");
		log("member1:    " + member.id + "," + member.name + "," +
				member.addr);
		
		member.setValue("ziyoun","지영","부산");
		log("member2:    " + member.id + "," + member.name + "," +
				member.addr);
		
		member.setValue("ziyoun","지영","부산");
		log("member2:    " + member.id + "," + member.name + "," +
				member.addr);
		
		var memberInfo = member.getValue();
		log("member3:    " + memberInfo)
		
		member.setId("hyolee");
		log("member.toString:    " + member.toString());
		
		var id = member.getId();
		log("member.getId():    " + id);
		
	}
	
	window.onload = function(){
		memberClass();
	}

</script>

</head>
<body>

<h1>Json이용 Javascript 클래스 사용</h1>

<div id="console"></div>

</body>
</html>