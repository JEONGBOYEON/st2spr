//Member 자바스크립트 클래스
Member = function(id,name,addr){
//멤버라는 클래스를 만들고 생성자로 초기화 한것과 같음	
	this.id = id;
	this.name = name;
	this.addr = addr;
	
};

//클래스 안에 함수 정의(setter)
Member.prototype.setValue = function(id,name,addr){
//클래스를 만들고 매소드로 초기화(셋터의 의미)
	this.id = id;
	this.name = name;
	this.addr = addr;
};

//클래스 안에 함수 정의(getter)
Member.prototype.getValue = function(){
	return "[" + this.id + "]" + this.name +"(" + this.addr + ")" ;
};

