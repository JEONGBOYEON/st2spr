<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	int su1 = Integer.parseInt(request.getParameter("su1"));
	int su2 = Integer.parseInt(request.getParameter("su2"));
	String oper = request.getParameter("oper");
	
	int sum = 0;
	
	if(oper.equals("hap"))
		sum = su1 + su2;
	else if(oper.equals("sub"))
		sum = su1 - su2;
	else if(oper.equals("mul"))
		sum = su1 * su2;	
	else if(oper.equals("div"))
		sum = su1 / su2;	
	
	StringBuffer sb = new StringBuffer();
	
	//xml파일 만들어 보내기(위에 처리작업 때문에)
	sb.append("<?xml version='1.0' encoding='UTF-8'?>\n");
	sb.append("<root>" + sum + "</root>");
	
	/* 값 돌려주기 */
	response.setContentType("text/xml;charset=utf-8");
	response.getWriter().write(sb.toString());
	
%>

<!-- 값을 받고 처리한 후 결과만 돌려주는 친구 : 서버?? -->

