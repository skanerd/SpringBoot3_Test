package com.mysite.sbb.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 데이터를 저장할 때 해당 속성에 값을 일일이 입력하지 않아도 자동으로 1씩 증가한다.
	// 고유한 번호를 생성하는 방법을 지정해준다. 이 속성(id)만 별도로 번호가 차례대로 늘어나게 한다.
	private Long id;
	
	@Column(unique = true)
	// 해당 컬럼의 속성 설정 - 유일한 값만 저장가능 (중복 불가능)
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;
}
