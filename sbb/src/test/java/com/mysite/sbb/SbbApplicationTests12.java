package com.mysite.sbb;

import com.mysite.sbb.question.QuestionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // 해당 클래스가 테스트 클래스임을 의미
class SbbApplicationTests12 {
	
	@Autowired
	private QuestionService questionService;
	
	@Test
	void testJpa() {
		// Junit 으로 확인할 때는 로컬서버를 종료하고 실행해야한다.
		
		// 대량 테스트 데이터 만들기 (후에 페이징을 통해 한 페이지에 정해진 갯수만 보이게)
		
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("Test Data:[%03d]", i);
			// [%03] -> 제목에 번호를 부여한다.
			String content = "no content";
			this.questionService.create(subject, content, null);
			// null이라고 작성하면 principal 객체에서 오류가 발생한다.
			// 이를 해결하기 위해 principal 객체를 사용하는 메소드에 @PreAuthorize("isAuthenticated()")어노테이션을 작성해야한다.
			// 해당 어노테이션을 메소드에 사용하면 해당 메소드는 로그인한 사용자만 호출이 가능해진다.
			// 로그아웃 상태에서 호출되면 로그인 페이지로 강제 이동된다. (QuestionController, AnswerController에 작성해준다.)
			
			
		}	// 테스트 케이스(더미 데이터)를 300개 생성
	}
}
