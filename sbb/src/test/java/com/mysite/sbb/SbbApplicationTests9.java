package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest // 해당 클래스가 테스트 클래스임을 의미
class SbbApplicationTests9 {
	
	@Autowired // 스프링의 의존성 주입 (DI) 기능을 사용하여 QuestionRepository의 객체를 주입한다.
	private QuestionRepository questionRepository;
	// QuestionRepository 데이터 타입의 변수 questionRepository는 비어있지만 
	// @Autowired 어노테이션을 통해 의존성이 주입되어 스프링 부트가 자동으로 해당 객체를 자동으로 만들어 주입해준다.
	// 순환 참조 문제 때문에 테스트 환경에서 주로 사용된다. (평소에는 Setter 혹은 생정자를 사용한다.)
	
	@Autowired
	private AnswerRepository answerRepository;
	// 질문 데이터를 저장할 때와 마찬가지로 답변도 리포지토리(AnswerRepository)가 필요하므로 @Autowired 해준다.
	
	@Test
	void testJpa() {
		// Junit 으로 확인할 때는 로컬서버를 종료하고 실행해야한다.
		
		// 방법 9 id로 구분한 질문에 답변 달기
		
		Optional<Question> oq = this.questionRepository.findById(2);
		// 답변을 생성하기 위해서는 먼저 질문을 조회할 필요가 있다.
		// id 가 2인 (1은 방법 7에서 내용을 수정하고 방법 8에서 삭제했다.) 질문 데이터를 가져와 답변의 question 속성에 대입에서 답변 데이터를 생성
		assertTrue(oq.isPresent());
		Question q = oq.get();
		// question 열에 데이터를 생성하려면 질문 데이터를 조회해야 하므로 위 3줄을 작성한다.
		
		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다."); // 답변 내용
		a.setQuestion(q);
		// 답변 Entity 의 question 속성에 질문 데이터를 대입해 답변 데이터를 생성하려면
		// Question 객체의 q 가 필요하다.
		a.setCreateDate(LocalDateTime.now()); // 답변 입력 시간
		this.answerRepository.save(a);
	}
}
