package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest // 해당 클래스가 테스트 클래스임을 의미
class SbbApplicationTests3 {
	
	@Autowired // 스프링의 의존성 주입 (DI) 기능을 사용하여 QuestionRepository의 객체를 주입한다.
	private QuestionRepository questionRepository;
	// QuestionRepository 데이터 타입의 변수 questionRepository는 비어있지만 
	// @Autowired 어노테이션을 통해 의존성이 주입되어 스프링 부트가 자동으로 해당 객체를 자동으로 만들어 주입해준다.
	// 순환 참조 문제 때문에 테스트 환경에서 주로 사용된다. (평소에는 Setter 혹은 생정자를 사용한다.)
	
	@Test
	void testJpa() {
		// Junit 으로 확인할 때는 로컬서버를 종료하고 실행해야한다.

		// id로 질문 조회 findById
		
		Optional<Question> oq = this.questionRepository.findById(1); // id 값 (1)으로 조회
		// 리턴 타입이 Question이 아니라 Optional이다.
		// findById의 값이 존재할 수도, 존재하지 않을 수도 있기에 리턴 타입을 Optional로 설정하였다.
		// Optional은 그 값을 처리하기 위한 (null값을 유연하기 처리하기 위해) 클래스이다.
		if(oq.isPresent()) {
			// isPresent() 를 통해서 값이 존재하는지 확인할 수 있다.
			// 만약 값이 존재한다면
			Question q = oq.get();
			// get() 메소드를 통해 실제 Question 객체의 값을 얻는다.
			assertEquals("sbb가 무엇인가요?", q.getSubject());
			
			// 즉, ID가 1인 Question을 검색하고ㅡ 이에 해당하는 질문의 '제목이 sbb가 무엇인가요?' 인 경우에 Junit 테스트를 통과하게 된다.
		}
	}
}
