package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page; // 페이징을 위한 클래스
import org.springframework.data.domain.Pageable; // 페이징을 처리하는 인터페이스
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	// QuestionRepository는 JpaRepository(CRUD)를 반드시 사용해야한다. (인터페이스)
	// QuestionRepository 인터페이스는 Question 엔티티를 다루며,
	// 주 키의 타입은 Integer이다.
	
	Question findBySubject(String subject);
	// 질문의 제목으로 sql내에서 검색할 수 있도록 해준다.
	
	Question findBySubjectAndContent(String subject, String content);
	// 질문의 제목과, 내용을 함께 sql내에서 검색할 수 있도록 해준다.
	
	List<Question> findBySubjectLike(String subject);
	// 특정 단어를 포함하는 제목을 sql내에서 검색할 수 있도록 해준다.
	
	Page<Question> findAll(Pageable pageable);
	// 모든 질문을 페이지 처리해준다.
	
	Page<Question> findAll(Specification<Question> qpec, Pageable pageable);
	// 질문 조회 결과를 페이징해준다.
	
	@Query("select "
			+ "distinct q "
			+ "from Question q "
			+ "left outer join SiteUser u1 on q.author=u1 "
			+ "left outer join Answer a on a.question=q "
			+ "left outer join SiteUser u2 on a.author=u2 "
			+ "where "
			+ "  q.subject like %:kw% "
			+ "  or q.content like %:kw% "
			+ "  or u1.username like %:kw% "
			+ "  or a.content like %:kw% "
			+ "  or u2.username like %:kw% ")
		Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
	// @Query는 테이블 기준이 아닌 엔티티 기준으로 작성해야한다.
	// 즉, site_user와 같은 테이블ㅇ 대신 SiteUser처럼 엔티티명을 사용해야한다.
	// join문에서 보듯이 q.author_id = u1.id와 같은 컬럼명 대신 q.author = u1처럼 엔티티 속성명을 사용해야한다.
	// @Query에 매개변수로 전달할 kw 문자열은 메소드의 매개변수에 @Param("kw")처럼 @Param 어노테이션을 사용해야한다.
}
