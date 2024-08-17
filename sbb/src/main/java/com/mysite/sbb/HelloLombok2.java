package com.mysite.sbb;

import lombok.Getter;
import lombok.Setter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Getter
@Setter

public class HelloLombok2 {
	private final String hello;
	private final int lombok;
	
//	public HelloLombok(String hello, int lombok) {
//		this.hello = hello;
//		this.lombok = lombok;
//	}
	// lombok을 사용하지 않았다면 생성자를 직접 생성해줘야한다. 이렇게.

	public static void main(String[] args) {
		HelloLombok2 helloLombok2 = new HelloLombok2("hello", 5);
		// RequiredArgsConstructor 를 import해주면 생성자를 만들 필요도 없이 바로 인자값을 넣어줄 수 있다.
		
//		helloLombok2.setHello("HELLO");
//		helloLombok2.setLombok(5);
		// hello와 lombok을 final로 선언했기에 생성자를 만들 수 없게 된다.
			
		System.out.println(helloLombok2.getHello());
		System.out.println(helloLombok2.getLombok());
	}
}