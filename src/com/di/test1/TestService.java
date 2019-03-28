package com.di.test1;

public class TestService {

	private Test test;
	
	//매개변수 의존성 주입 = 결과적으로 셋터 만든것
	public void setTest(Test test){
		this.test = test;
	}
	
	public TestService() {
		
	}
	
	
	//생성자로 DI(의존성 주입)
	//호출할때 매개변수에 어떤 클래스(TestImpl1,TestImpl2)가 들어가는지에 따라 result결과가 달라짐
	public TestService(Test test){
		this.test = test;
	}
	
	public String getValue(){
		//결과적으로 내꺼(TestImpl1,TestImpl2)의 result(오버라이딩했기 때문에)가 호출된다
		return test.result();
	}
	
	
}
