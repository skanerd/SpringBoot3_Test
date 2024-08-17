package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest // 해당 클래스가 테스트 클래스임을 의미
class SbbApplicationTests11 {
	
	@Autowired // 스프링의 의존성 주입 (DI) 기능을 사용하여 QuestionRepository의 객체를 주입한다.
	private QuestionRepository questionRepository;
	// QuestionRepository 데이터 타입의 변수 questionRepository는 비어있지만 
	// @Autowired 어노테이션을 통해 의존성이 주입되어 스프링 부트가 자동으로 해당 객체를 자동으로 만들어 주입해준다.
	// 순환 참조 문제 때문에 테스트 환경에서 주로 사용된다. (평소에는 Setter 혹은 생정자를 사용한다.)
	
	@Transactional // 메소드가 종료될 때까지 DB세션이 유지된다.
	@Test
	void testJpa() {
		// Junit 으로 확인할 때는 로컬서버를 종료하고 실행해야한다.

		// 11. 질문 데이터를 통해서 답변 데이터 찾기
		
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		
		List<Answer> answerList = q.getAnswerList(); // q.getAnswerList() q(질문)에서 답변리스트를 구하고 있다.
		
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
		// 위의 6줄을 그대로 실행하면 Junit에서 오류가 발생한다.
		// QuestionRepository가 findById 메소드를 통해 Question 객체를 조회하고 나면 DB세션이 끊어지기 때문이다.
		// q.getAnswerList()가 실행될 차례에 이미 DB세션이 종료되어 있어서 오류가 발생한다. (DB세션이 끊어지는건 테스트 환경에서만 그렇다.)
		// @Transactional 어노테이션을 사용하면 메소드가 종료될 때까지 DB세션을 유지할 수 있다.
	}
}
