package com.di.test1;

public class TestService {

	private Test test;
	
	//�Ű����� ������ ���� = ��������� ���� �����
	public void setTest(Test test){
		this.test = test;
	}
	
	public TestService() {
		
	}
	
	
	//�����ڷ� DI(������ ����)
	//ȣ���Ҷ� �Ű������� � Ŭ����(TestImpl1,TestImpl2)�� �������� ���� result����� �޶���
	public TestService(Test test){
		this.test = test;
	}
	
	public String getValue(){
		//��������� ����(TestImpl1,TestImpl2)�� result(�������̵��߱� ������)�� ȣ��ȴ�
		return test.result();
	}
	
	
}
