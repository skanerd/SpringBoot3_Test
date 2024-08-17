package com.mysite.sbb.answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	// AnswerRepository는 JpaRepository(CRUD)를 반드시 사용해야한다. (인터페이스)
	// AnswerRepository 인터페이스는 Answer 엔티티를 다루며,
	// 주 키의 데이터 타입은 Integer이다.
}
