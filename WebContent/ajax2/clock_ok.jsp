<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	//캐쉬제거
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-Control","no-store");//HTTP1.0
	response.setHeader("Cache-Control","no-cache");//HTTP1.1
	

	//세션삭제 후 (로그아웃) 뒤로가기 방지
	response.setDateHeader("Expires", 0);

%>

<%=new Date()%>