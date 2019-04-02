<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%!
/*  */
	//이 데이터는 원래 DB에서 읽어온다
	String keywords[] = {"ajax","Ajax","Ajax 실전 프로그래밍","AZA","AZERA",
						"자수","자전거","자바 프로그래밍","자바 서버 페이지",
						"자바캔","ABC마트","APPLE"};

	public List<String> search(String userKeyword){
		
		if(userKeyword==null || userKeyword.equals("")){
			return null;
			//return Collection.EMPTY_LIST;	//이것도 가능
		}
		
		//userKeyword = userKeyword.toUpperCase();	//대문자 검사
		
		List<String> lists = new ArrayList<String>();
		for(int i=0;i<keywords.length;i++){
			
			if(keywords[i].startsWith(userKeyword)){
				lists.add(keywords[i]);
			}
		}
		return lists;
		
	}
%>
<%
	String userKeyword = request.getParameter("userKeyword");

	List<String> keywordList = search(userKeyword);
	
	out.print(keywordList.size());
	
	out.print("|");
	
	Iterator<String> it = keywordList.iterator();
	
	while(it.hasNext()){
		
		String value = (String)it.next();
		out.print(value);
		
		//마지막 검색어 전까지 ,가 찍히도록
		if(keywordList.size()-1>0){
			out.print(",");
		}
		
	}
%>