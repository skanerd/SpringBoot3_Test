package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page; // 페이징을 위한 클래스
import org.springframework.data.domain.PageRequest; // 현재 페이지와 한 페이지에 보여줄 게시물 개수 등을 설정하여 페이징 요청을 하는 클래스
import org.springframework.data.domain.Pageable; // 페이징을 처리하는 인터페이스
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
// QuestionController에서 QuestionRepository를 직접 접근해 질문 목록 데이터를 조회했지만
// 좀 더 모듈화하기 위해, 엔티티(Question, Answer 클래스)에 담긴 민감한 데이터를 노출 시키지 않기 위해
// 컨트롤러에서 직접 호출하지 않고 중간에 서비스를 두어 데이터를 처리하게 한다. 
public class QuestionService {

	private final QuestionRepository questionRepository;
	
	private Specification<Question> search(String kw) { // 검색 기능 
		// kw를 입력받아 쿼리의 join과 where문을 Specification 객체로 생성하여 리턴한다.
		return new Specification<>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// q Root의 자료형으로, 기준이 되는 Question 엔티티의 객체를 의미하며 질문 제목과 내용을 검색한다.
				query.distinct(true); // 중복 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				// u1 Question엔티티와 SiteUser 엔티티를 아우터 조인(여기서는 JoinType.LEFT로 아우터 조인)
				// 하여 만든 SiteUser엔티티의 객체
				// Question엔티티와 SiteUser 엔티티는 author 속성으로 연결되어 있어서 q.join("author")와 같이 조인한다.
				// u1 객체는 질문 작성자를 검색하기 위해 필요하다.
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				// a Question 엔티티와 Answer 엔티티를 아우터 조인하여 만든 Answer 엔티티의 객체이다.
				// Question 엔티티와 Answer 엔티티는 answerList 속성으로 연결되어 있어서 q.join("answerList")와 같이 조인한다.
				// a 객체는 답변 내용을 검색할 때 필요하다.
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				// a 객체와 다시 한번 SiteUser 엔티티와 아우터 조인하여 만든 SiteUSer 엔티티의 객체이다.
				// u2 객체는 답변 작성자를 검색할 때 필요하다.
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
					cb.like(q.get("content"), "%" + kw + "%"), // 질문 내용
					cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
					cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
					cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
			}
		};
	}
	
	
	public Page<Question> getList(int page, String kw) {
		// 질문 목록을 조회하는 getList()를 
		// 정수 타입의 페이지 번호를 입력받아 해당 페이지의 Page 객체를 리턴하도록 수정
		List<Sort.Order>sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		// 작성 일시 역순(최신순)을 위해 dese("createDate")를 작성해준다. asc 오름차순
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		// (page, 10)의 page는 조회할 페이지의 번호, 10은 한 페이지에 보여줄 게시물 개수
		// 데이터 전체를 조회하지 않고 해당 페이지의 데이터만 조회하도록 쿼리가 변경된다.
		// 게시물을 역순(최신순)으로 조회하려면 PageRequest.of 메소드에 세 번째 매개변수에 sort를 넣어주어야한다.
		
//		Specification<Question> spec = search(kw);		
//		return this.questionRepository.findAll(spec, pageable);
		return this.questionRepository.findAllByKeyword(kw, pageable);
	}

	
//	public List<Question> getList() {
//		return this.questionRepository.findAll();
//		// 질문 목록 데이터 조회하여 리턴
//		// (QuestionController)에서 리포지토리를 사용했던 부분을 그대로 옮겨놨다.
//	}
	
	public Question getQuestion(Integer id) { // id 값으로 질문 데이터를 조회하기 위한 메소드
		Optional<Question> question = this.questionRepository.findById(id);
		// 리포지토리로 얻은 Question 객체는 Optional 객체이므로
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		} // if, else 문을 통해 isPresent 메소드로 해당 데이터 (여기서는 id)가 존재하는지 확인하는 과정을 거친다.
		// 만약 존재하지 않는다면 예외 클래스 DataNotFoundException이 실행된다. 
	}
	
	public void create(String subject, String content, SiteUser user) {
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		this.questionRepository.save(q);
	}
	
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
}

	
