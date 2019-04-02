

//jsp파일은 리턴값을 받아서 객체 생성
function getXMLHttpRequest(){
	
	if(window.ActioveXObject){
		
		try {
			
			return new ActiveXObject("Msxml2.XMLHTTP");
			
		} catch (e) {
			
			try {
				
				//5.0이전 버전
				return new ActiveXObject("Microsoft.XMLHTTP");
				
			} catch (e) {
				return null;
			}
			
		}
		
	}else if(window.XMLHttpRequest){
	
		return new XMLHttpRequest();
	
	}else{
		
		return null;
		
	}
	
}


//Ajax 요청 처리
var httpRequest = null;

function sendRequest(url,params,callback,method){
	
	httpRequest = getXMLHttpRequest();
	
	//method 처리
	//method값이 있으면 넘어온 method값을 사용. 없으면 GET으로 설정
	var httpMethod = method?method:"GET";
	
	if(httpMethod!="GET" && httpMethod!="POST"){
		httpMethod = "GET";
	}
	
	//params 처리
	var httpParams = (params==null || params=="")?null:params;
	
	//url처리
	var httpUrl = url;
	if(httpMethod=="GET" && httpParams!=null){
		httpUrl += "?" + httpParams;
	}
	
	httpRequest.open(httpMethod,httpUrl,true);
	//POST방식일 경우를 대비해서 작성
	httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	httpRequest.onreadystatechange = callback;
	httpRequest.send(httpMethod=="POST"?httpParams:null);	
	
}