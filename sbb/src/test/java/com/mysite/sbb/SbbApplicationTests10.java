package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;

@SpringBootTest // 해당 클래스가 테스트 클래스임을 의미
class SbbApplicationTests10 {
	
//	@Autowired // 스프링의 의존성 주입 (DI) 기능을 사용하여 QuestionRepository의 객체를 주입한다.
//	private QuestionRepository questionRepository;
	// QuestionRepository 데이터 타입의 변수 questionRepository는 비어있지만 
	// @Autowired 어노테이션을 통해 의존성이 주입되어 스프링 부트가 자동으로 해당 객체를 자동으로 만들어 주입해준다.
	// 순환 참조 문제 때문에 테스트 환경에서 주로 사용된다. (평소에는 Setter 혹은 생정자를 사용한다.)
	
	@Autowired
	private AnswerRepository answerRepository;
	// 질문 데이터를 저장할 때와 마찬가지로 답변도 리포지토리(AnswerRepository)가 필요하므로 @Autowired 해준다.
	
	@Transactional
	@Test
	void testJpa() {
		// Junit 으로 확인할 때는 로컬서버를 종료하고 실행해야한다.

		// 10. 답변 데이터 조회
		// 답변데이터도 질문과 마찬가지로 id속성이 기본키(자동생성)이므로 findById() 메소드를 이용해 조회가 가능하다.
		
		Optional<Answer> oa = this.answerRepository.findById(1); // 답변(answerRepository)의 id가 1인 답변을 조회한다.
		assertTrue(oa.isPresent()); // 값이 존재하는 지를 확인하는 메소드
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId()); // 조회한 답변 (답변 id 1) 의 질문의 id가 2인지 물어보고있다.	
	}
}