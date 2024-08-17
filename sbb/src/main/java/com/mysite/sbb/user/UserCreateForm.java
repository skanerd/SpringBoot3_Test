package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	@Size(min = 3, max = 25) // 유저 네임의 검증조건
	@NotEmpty(message = "사용자 ID는 필수 항목입니다.")
	private String username;
	
	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	private String password1; // 1이 붙는 이유는 1,2 로 2번 입력을 통해 비밀번호를 한 번 더 확인하기 위해
	
	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	private String password2;
	
	@NotEmpty(message = "email은 필수 항목입니다.")
	@Email // 입력 값이 이메일 형식인지를 확인
	private String email;
}
