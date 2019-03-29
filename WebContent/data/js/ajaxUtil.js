var xmlHttp;

function createXMLHttpRequest(){
	
	if(window.XMLHttpRequest){
		
		xmlHttp = new XMLHttpRequest();	//non IE
		
	} else if(window.ActiveXObject){
		
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")	//IE 5.0이후			
		} catch (e) {
			var xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");	//IE 5.0이전
		}
		
	} 
	
	return xmlHttp;
	
}