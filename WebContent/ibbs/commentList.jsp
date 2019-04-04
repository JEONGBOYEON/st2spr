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
</head>
<body>
<c:if test="${totalDataCount!=0 }">

<table width="600" border="0" cellpadding="0" cellspacing="0"
align="center">

<c:forEach var="dto" items="${lists }">

<tr>
	<td colspan="2" bgcolor="#999999" height="1"></td>
</tr>

<tr height="30" bgcolor="#dbdbdb">
	<td width="300" style="padding-left: 10px;">
		<b>No ${dto.listNum }</b>&nbsp;&nbsp;.${dto.name }
	</td>
	<td width="300" align="right" style="padding-right: 10px;">
		${dto.created }&nbsp;
		<a href="javascript:deleteData('${dto.commentNum }','${pageNum }')">삭제</a>
	</td>
</tr>

<tr>
	<td height="30" style="padding-left: 10px;" colspan="2">
		${dto.content }
	</td>
</tr>

</c:forEach>

<tr>
	<td colspan="2" bgcolor="#dbdbdb" height="2"></td>
</tr>

<tr height="20">
	<td align="center" colspan="2">
		${pageIndexList }
	</td>
</tr>

</table>

</c:if>

</body>
</html>