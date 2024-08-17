package com.mysite.sbb;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
// get set 사용을 위해

@RequiredArgsConstructor

public class Book {
	private final String Title;
	private final String Author;
	
	public static void main(String[] args) {
		Book Book = new Book("a","b");
		
		System.out.println(Book.getTitle());
		System.out.println(Book.getAuthor());
	}
}


// 교재 50페이지 예제