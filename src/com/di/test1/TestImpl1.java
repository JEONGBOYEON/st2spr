package com.di.test1;

public class TestImpl1 implements Test{

	private int su1;
	private int su2;
	
	public TestImpl1() {
		su1 = 10;
		su2 = 20;
	}
	
	public TestImpl1(int su1, int su2) {
		this.su1 = su1;
		this.su2 = su2;
	}

	@Override
	public String result() {
		
		String str = String.format("%d + %d = %d",su1,su2,su1+su2);
		
		return str;
	}
	
	
}
