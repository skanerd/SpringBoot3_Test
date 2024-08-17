package com.mysite.sbb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Practice_01_02_Book {
	private String title;
	private String author;
	
	
	// .p50
	
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	public String setAuthor() {
//		return author;
//	}
//	public void setAuthor(String author) {
//		this.author = author;
//	}
	
	// 기존에는 이렇게 get set이 다 작성되어 있는 코드였지만
	// lombok을 import해주면 알아서 get set이 있는 것 처럼 해주기에 get set을 작성할 필요없이 어노테이션을 통해 바로바로 사용가능하다.
}
