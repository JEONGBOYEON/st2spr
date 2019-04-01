<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();

	String userId = request.getParameter("userId");
	String userPwd = request.getParameter("userPwd");

	String result = "";
	
	for(int i=1; i<=3; i++){
		//xml데이터를 가져오거나 DB를 가져오거나
		//가져온 데이터를 json방식으로 만들어서 바꿔주는 작업
		//바꿔준 후 다시 데이터를 클라이언트에게 넘겨줌
		
		result += "{\"id\":\"" + i ;
		result += "\",\"userId\":\"" + userId;
		result += "\",\"userPwd\":\"" + userPwd + "\"},";
		
		//json방식 = {key1:value1,key2:value2,..},{},{}...
				
		//[{"id":"1","userId":"suzi","userPwd":"a123"},
		//{id:1,userId:suzi,userPwd:a123},
		//{id:1,userId:suzi,userPwd:a123}]
		

	}		
	
	result = result.substring(0,result.length()-1);
	result = "[" + result + "]";
	
	out.print(result);
	
%>
