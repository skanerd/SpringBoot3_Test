package com.mysite.sbb;

import lombok.Getter;
import lombok.Setter;
//롬복을 사용하면 속성에 대한 Getter, Setter 메소드를 별도로 작성하지 않아도 사용가능하다.

@Getter
@Setter
// 어노테이션을 작성해서 set get 메소드를 바로 사용할 수 있게 되었다.

public class HelloLombok {
	private String hello;
	private int lombok;
	// 클래스의 속성을 추가한다.

	public static void main(String[] args) {
		// 인스턴스 생성
		HelloLombok helloLombok = new HelloLombok();

		helloLombok.setHello("HELLO");
		helloLombok.setLombok(5);
		
		System.out.println(helloLombok.getHello());
		System.out.println(helloLombok.getLombok());
	}
}

// lombok을 import하고 어노테이션을 사용함으로서

//public void setHello(String hello) {
//	this.hello = hello;
//}
//
//public void setLombok(int lombok) {
//	this.lombok = lombok;
//}
//
//public void getHello(String hello) {
//	return this.hello;
//}
//
//public void getLombok(int lombok) {
//	return this.lombok;
//}

// 을 작성할 필요가 없어졌다.
