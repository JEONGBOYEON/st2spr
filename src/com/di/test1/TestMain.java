package com.di.test1;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TestMain {

	public static void main(String[] args) {
		
		/* ���1 : 
		//�⺻�����ڴϱ� 10�� 20�� ������
		TestImpl1 ob1 = new TestImpl1();
		System.out.println(ob1.result());
		
		//�⺻�����ڴϱ� "�����", 25�� �������
		TestImpl2 ob2 = new TestImpl2();
		System.out.println(ob2.result());
		*/
		
		/* ���2 :
		//�������̽��� ��ĳ����
		Test ob;
		
		ob = new TestImpl1();
		System.out.println(ob.result());
		
		ob = new TestImpl2();
		System.out.println(ob.result());
		*/
		
		/* ���3 : 
		//TestService.java��� �������� ����� �����ͼ� ȣ���ϱ�
		 
		*/
		

		String path = "com/di/test1/applicationContext.xml";
		Resource src = new ClassPathResource(path);
		
		
		//������ �����̳� ���� : ������ ������ 
		//������ �ϸ� factory�ȿ��� .xml�� ������ 2���� ��ü�� �� �ִ�
		BeanFactory factory = new XmlBeanFactory(src);
		
		
		//������ �����̳ʿ��� bean��ü�� ������
		//���⼭ new ������ �ʰ� 'xml�� bean�߰�'�Ѱɷ� �ؼ� new�� �Ѱſ� ���� ��ü�� �����Ѵ�
		TestService ob = (TestService)factory.getBean("testService");
		System.out.println(ob.getValue());
		
		
		
		
	}

}
