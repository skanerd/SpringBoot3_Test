package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest // 해당 클래스가 테스트 클래스임을 의미
class SbbApplicationTests2 {
	
	@Autowired // 스프링의 의존성 주입 (DI) 기능을 사용하여 QuestionRepository의 객체를 주입한다.
	private QuestionRepository questionRepository;
	// QuestionRepository 데이터 타입의 변수 questionRepository는 비어있지만 
	// @Autowired 어노테이션을 통해 의존성이 주입되어 스프링 부트가 자동으로 해당 객체를 자동으로 만들어 주입해준다.
	// 순환 참조 문제 때문에 테스트 환경에서 주로 사용된다. (평소에는 Setter 혹은 생정자를 사용한다.)

	@Test
	void testJpa() {
		// Junit 으로 확인할 때는 로컬서버를 종료하고 실행해야한다.

		// 2. 질문 데이터 조회하기 findAll()
		
		List<Question> all = this.questionRepository.findAll();
		// 모든 데이터를 조회하기 위해 questionRepository.findAll()을 사용한다.
		assertEquals(2, all.size());
		// assertEquals 메소드는 Junit의 메소드로서,
		// 테스트의 예상 결과와 실제 결과가 동일한지를 확인한다.
		// assertEquals(기댓값, 실제값) 방법으로 사용한다.
		// 질문이 2개이기에 데이터 사이즈도 2이다.
		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
		// 데이터의 내용도 확인이 가능하다.
	}
}
