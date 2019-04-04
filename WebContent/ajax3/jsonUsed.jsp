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

<style type="text/css">
	#result{
		border:1px dotted #0000ff;
	}
	
	div{
		margin: auto;
		width: 600px;
		height: 100%;
	}

</style>

<script type="text/javascript">
	
	function useJson(){
		
		var userArray = [
			{
				userId:"suzi",
				name:"배수지",
				age:25,
				phone:[
				      {home:["031-111-1111","032-222-2222"]},
				      {office:["02-333-3333","051-444-4444"]} 
				      ]
			},//배열0                 
			{	
				userId:"songhee",
				name:"천송이",
				age:38,
				phone:[{},{}]       
			},//배열1
			{	
				userId:"zhihyo",
				name:"송지효",
				age:37,
				phone:[{},{}]       
			}//배열2  
        ];
		
		var id = userArray[0].userId;
		var name = userArray[0].name;
		var age = userArray[0].age;
		var homePhone1 = userArray[0].phone[0].home[0]; //031-111-1111
		var officePhone1 = userArray[0].phone[1].office[0]; //032-222-2222
		
		var printData = id + "," + name + "," + age + "<br/>";
		printData += "userArray[0].phone[0].home[0]:" + homePhone1 + "<br/>";
		printData += "userArray[0].phone[1].office[0]:" + officePhone1 + "<br/>";
		
		for(var i=0;i<userArray.length;i++){

			var id = userArray[i].userId;
			var name = userArray[i].name;
			var age = userArray[i].age;

			printData += id + "," + name + "," + age + "<br/>";
			
		}
		
		var resultDiv = document.getElementById("result");
		resultDiv.innerHTML = printData;
	}
	
	window.onload = function(){
		useJson();
	}
	
</script>

</head>
<body>

<h1>JSON(Javascript Object Notation)</h1>

<hr/>

<div id="result"></div>

</body>
</html>