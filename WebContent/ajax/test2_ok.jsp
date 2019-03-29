<%@ page contentType="text/xml; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();

	String subject = request.getParameter("subject");
	String content = request.getParameter("content");
	
%>

<root>

	<status>권장도서</status>
	<record id="1">
		<subject><%=subject %></subject>
		<content><%=content %></content>
	</record>
	
	<record id="2">
		<subject>피구왕 통키</subject>
		<content>스포츠</content>
	</record>
	
</root>