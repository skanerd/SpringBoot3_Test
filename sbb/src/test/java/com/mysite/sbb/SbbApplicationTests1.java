package com.mysite.sbb;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest // 해당 클래스가 테스트 클래스임을 의미
class SbbApplicationTests1 {
	
	@Autowired // 스프링의 의존성 주입 (DI) 기능을 사용하여 QuestionRepository의 객체를 주입한다.
	private QuestionRepository questionRepository;
	// QuestionRepository 데이터 타입의 변수 questionRepository는 비어있지만 
	// @Autowired 어노테이션을 통해 의존성이 주입되어 스프링 부트가 자동으로 해당 객체를 자동으로 만들어 주입해준다.
	// 순환 참조 문제 때문에 테스트 환경에서 주로 사용된다. (평소에는 Setter 혹은 생정자를 사용한다.)

	@Test
	void testJpa() {
		// Junit 으로 확인할 때는 로컬서버를 종료하고 실행해야한다.
		
		// 1. 질문 데이터 저장하기
		
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		
		this.questionRepository.save(q1);
		// questionRepository.save()를 통해 question 테이블의 첫 번째 행으로 들어갈 데이터로
		// 위 코드에 작성한 데이터들을 저장한다.
			
		Question q2 = new Question();
		q2.setSubject("스프링 부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		
		this.questionRepository.save(q2);
		// questionRepository.save()를 통해 question 테이블의 두 번째 행으로 들어갈 데이터로
		// 위 코드에 작성한 데이터들을 저장한다.
	}

}
