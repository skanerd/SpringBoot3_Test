package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
	@NotEmpty(message="제목은 필수 사항입니다.") // null, 공백 ("")을 방지
	@Size(max=200) // 문자 길이 제한 200바이트
	private String subject;
	
	@NotEmpty(message="내용은 필수 항목입니다.")
	private String content;
}
