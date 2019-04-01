<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String protocal = request.getProtocol();
	System.out.print(protocal);
	
	//클라이언트 캐쉬 제거
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-Control","no-store");//HTTP1.0
	response.setHeader("Cache-Control","no-cache");//HTTP1.1
	/* 	
	if(request.getProtocol().equals("HTTP/1.1")){
		response.setHeader("Cache-Control","no-cache");//HTTP1.1	
	}	
 	*/	

	//세션삭제 후 (로그아웃) 뒤로가기 방지
	response.setDateHeader("Expires", 0);

%>

<%

	String greeting = request.getParameter("greeting");

	for(int i=0;i<3500;i++){
		System.out.print("기다려...처리중이야");
	}
	
%>

<%="Server : " + greeting%>