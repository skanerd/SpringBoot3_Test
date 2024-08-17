package com.mysite.sbb.user;

import lombok.Getter;

@Getter // 권한의 상수는 변경할 필요가 없으므로 @Setter 는 작성하지 않는다.
public enum UserRole { // enum 자료형 (열거 자료형)
	// UserRole = 사용자 권한
	ADMIN("ROLE_ADMIN"), // 관리자 권한
	USER("ROLE_USER"); // 사용자 권한
	
	UserRole(String value) {
		this.value = value;
	}
	
	private String value;
}
