<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%!

	String[] newsTitle={
		"트와이스, 드디어 도쿄돔 밟았다…10만 관객 열광",
		"강정호, 시즌 첫 2루타…2G 연속 멀티 출루",
		"구하라, 네티즌 성형 의혹에 안검하수 한게 죄?",
		"미우새, 배정남X엄정화 절친케미…日 예능 1위",
		"권창훈·이재성, 나란히 선발출전…소속팀은 고배",
		"리버풀전 교체 출전 손흥민, 평점 6",
		"강한나, 분쟁 끝 판타지오로 복귀"
	};

%>

<%
	//데이터의 갯수 | "내용1[날짜]" * "내용2[날짜]" * ...
	//7 | "트와이스, 드디어 도쿄돔 밟았다…10만 관객 열광[2019.02.02]" *
	//"강정호, 시즌 첫 2루타…2G 연속 멀티 출루[2015.04.03]" * ...
	
	out.print(newsTitle.length + "|"); //뉴스 갯수
	
	for(int i=0;i<newsTitle.length;i++){
		
		out.print(newsTitle[i] + "[" + new Date() + "]");
		
		if(i!=(newsTitle.length-1)){
			out.print("*");
		}
		
	}
%>