package com.mysite.sbb.answer;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
//	@PostMapping("/create/{id}")
//	// QuestionController.java처럼 @PostMapping(value="/create/{id}") 로도 작성할 수 있다. value는 생략가능
	
//	public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
//		// @RequestParam 부분은 question_detail.html에서 답변으로 입력한 내용(content)을 얻기 위해 추가 작성하였다.
//		// 템플릿의 답변 내용에 해당하는 <textarea>의 name 속성이 content이기에 여기서도 변수명을 content라고 작성하였다.
//		// /create/{id}에서 {id}는 질문 엔티티의 id이므로 이 id값으로 질문을 조회한다. (없으면 404)
	
//		Question question = this.questionService.getQuestion(id);
//		this.answerService.create(question, content);
//		return String.format("redirect:/question/detail/$s", id);
	
	@PreAuthorize("isAuthenticated()") // 로그인 유저만 호출가능, 비로그인 유저 -> 로그인 페이지로 이동
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id,
			@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
//		Valid와 bindingResult로 검증
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			// 답변 등록 페이지는 question객체가 필요하다.
			return "question_detail";
			// 검증 실패시 답변등록 페이지로 
		}
		Answer answer = this.answerService.create(question, answerForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s#answer_%s",
				answer.getQuestion().getId(), answer.getId());
		
		// 전체적인 양식은 QuestionController과 같다. 설명은 그 쪽 참고
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		answerForm.setContent(answer.getContent());
		return "answer_form";
	} // DB에서 답변 id를 통해 조회한 답변 데이터의 내용(content)을 answerForm 객체에 대입하여 answer_form.html 템플릿에서 사용.
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "answer_form";
		}
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		this.answerService.modify(answer, answerForm.getContent());
		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
		}
		this.answerService.delete(answer);
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		SiteUser siteUSer = this.userService.getUser(principal.getName());
		this.answerService.vote(answer, siteUSer);
		return String.format("redirect:/question/detail/%s#answer_$s", answer.getQuestion().getId(), answer.getId());
	}
}
