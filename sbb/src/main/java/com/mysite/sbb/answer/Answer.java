package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne; // 답변을 통해 질문의 제목을 알 수 있다. (질문과 연결된 속성 표시)
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 데이터를 저장할 때 해당 속성에 값을 일일이 입력하지 않아도 자동으로 1씩 증가한다.
	// 고유한 번호를 생성하는 방법을 지정해준다. 이 속성(id)만 별도로 번호가 차례대로 늘어나게 한다.
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	@ManyToOne // 질문과 연결 (여러개의 답변Many - 하나의 질문 One)
	private Question question; // 답변 Entity 에서 질문 Entity 참조하기 위해 question 속성을 추가한다.
	
	@ManyToOne
	private SiteUser author;
	// 글쓴이 항목 추가
	
	private LocalDateTime modifyDate; 
	// 답변 수정 일시 추가
	
	@ManyToMany
	Set<SiteUser> voter;
	// 질문 또는 답변에 추천 기능을 구현한다.
	// 하나의 질문에 여러 사람이 추천할 수 있고, 한 사람이 여러 개의 질문에 추천할 수 있게하기 위해 ManyToMany를 어노테이션 한다.
	// Set 자료형으로 작성한 이유는 voter 속성값이 중복되지 않도록 하기 위해서이다.
	// List 잘형과 달리 여기서는 Set 자료형이 voter 속성을 관리하는데 효율적이기 때문이다.
}
