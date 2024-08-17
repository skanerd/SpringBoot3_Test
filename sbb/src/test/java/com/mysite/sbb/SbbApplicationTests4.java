package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest // 해당 클래스가 테스트 클래스임을 의미
class SbbApplicationTests4 {
	
	@Autowired // 스프링의 의존성 주입 (DI) 기능을 사용하여 QuestionRepository의 객체를 주입한다.
	private QuestionRepository questionRepository;
	// QuestionRepository 데이터 타입의 변수 questionRepository는 비어있지만 
	// @Autowired 어노테이션을 통해 의존성이 주입되어 스프링 부트가 자동으로 해당 객체를 자동으로 만들어 주입해준다.
	// 순환 참조 문제 때문에 테스트 환경에서 주로 사용된다. (평소에는 Setter 혹은 생정자를 사용한다.)
	
	@Test
	void testJpa() {
		// Junit 으로 확인할 때는 로컬서버를 종료하고 실행해야한다.

		// 방법 4 질문의 subject 로 조회
		
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
		
		// findBySubject() 메소드를 선언한 하고 방법 3 처럼 구현을 해주지 않았는데
		// 작동하는 이유는 JPA에 리포지토리의 메소드명을 분석하여 쿼리를 만들고 실행하는 기능이 있기 때문이다.
		// findBy + 엔티티의 속성명 (여기서는 Subject를 작성하여 findBySubject) 과 같은 리포지토리의 메소드를 작성하면
		// 입력한 속성의 값으로 데이터를 조회할 수 있다.	
	}
}
