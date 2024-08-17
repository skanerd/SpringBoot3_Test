package com.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
// 예외가 발생하면 응답을 생성하여 클라이언트에게 반환한다. (여기서는 404 오류)
public class DataNotFoundException extends RuntimeException { // 특정 엔티티 또는 데이터를 찾을 수 없을 때 발생시키는 예외 클래스
	private static final long serialVersionUID = 1L;
	public DataNotFoundException(String message) {
		super(message);
	}
}
