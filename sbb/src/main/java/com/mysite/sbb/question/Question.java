package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; // 질문(One)에서 답변(Many) Entity를 참조할 수 있게 해준다.
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 데이터를 저장할 때 해당 속성에 값을 일일이 입력하지 않아도 자동으로 1씩 증가한다.
	// 고유한 번호를 생성하는 방법을 지정해준다. 이 속성(id)만 별도로 번호가 차례대로 늘어나게 한다.
	private Integer id;
	
	@Column(length = 200) // Column(열) 설정 - 길이 200
	private String subject;
	
	@Column(columnDefinition = "TEXT") // Coulmn(열) 유형 설정 TEXT 를 넣을 것이고, 글자 수 제한이 없다.
	private String content;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	// cascade = CascadeType.REMOVE 는 질문이 삭제되면 관련 답변들도 삭제되게 해준다.
	private List<Answer> answerList;
	
	@ManyToOne // 사용자 한 명이 질문 여러개를 작성할 수 있다.	
	private SiteUser author;
	// 글쓴이 항목 추가
	
	private LocalDateTime modifyDate;
	// 질문 수정 일시 추가

	@ManyToMany
	Set<SiteUser> voter;
	// 질문 또는 답변에 추천 기능을 구현한다.
	// 하나의 질문에 여러 사람이 추천할 수 있고, 한 사람이 여러 개의 질문에 추천할 수 있게하기 위해 ManyToMany를 어노테이션 한다.
	// Set 자료형으로 작성한 이유는 voter 속성값이 중복되지 않도록 하기 위해서이다.
	// List 잘형과 달리 여기서는 Set 자료형이 voter 속성을 관리하는데 효율적이기 때문이다.
}
