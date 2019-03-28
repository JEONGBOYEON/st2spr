package com.di.test1;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TestMain {

	public static void main(String[] args) {
		
		/* 방법1 : 
		//기본생성자니까 10과 20이 들어가있음
		TestImpl1 ob1 = new TestImpl1();
		System.out.println(ob1.result());
		
		//기본생성자니까 "배수지", 25가 들어있음
		TestImpl2 ob2 = new TestImpl2();
		System.out.println(ob2.result());
		*/
		
		/* 방법2 :
		//인터페이스로 업캐스팅
		Test ob;
		
		ob = new TestImpl1();
		System.out.println(ob.result());
		
		ob = new TestImpl2();
		System.out.println(ob.result());
		*/
		
		/* 방법3 : 
		//TestService.java라는 완충지대 만들고 가져와서 호출하기
		 
		*/
		

		String path = "com/di/test1/applicationContext.xml";
		Resource src = new ClassPathResource(path);
		
		
		//스프링 컨테이너 생성 : 일종의 번역기 
		//저렇게 하면 factory안에는 .xml에 생성한 2개의 객체가 들어가 있다
		BeanFactory factory = new XmlBeanFactory(src);
		
		
		//스프링 컨테이너에서 bean객체를 가져옴
		//여기서 new 해주지 않고 'xml에 bean추가'한걸로 해서 new를 한거와 같이 객체를 생성한다
		TestService ob = (TestService)factory.getBean("testService");
		System.out.println(ob.getValue());
		
		
		
		
	}

}
