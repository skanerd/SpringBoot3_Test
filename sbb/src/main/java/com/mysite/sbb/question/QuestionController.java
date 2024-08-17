package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question") // List() detail() 메소드의 매핑을 보면 URL의 프리픽스가 모두 /question으로 시작되고 있다.
								// RequestMapping 을 작성함으로서 /question을 생략할 수 있다.

@RequiredArgsConstructor // 해당 어노테이션의 생성자 방식으로 questionRepository 객체를 주입했다.
							// lombok이 제공하는 어노테이션 final이 붙은 속성을 포함하는 생성자를 자동으로 만들어준다.
							// 스프링부트가 내부적으로 QuestionController를 생성할 때 롬복으로 만들어진 생성자에 의해 questionRepository
							// 객체가 자동 주입된다.
@Controller
public class QuestionController {

	private final QuestionService questionService;
	private final UserService userService;

//	@GetMapping("/question/list")
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {
//		get 방식으로 요청된 url에서 page 값을 가져오기 위해 list 메소드의 매개변수로 @RequestParam(...)을 추가하였다.
		Page<Question> paging = this.questionService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "question_list";
	}

//	public String list(Model model) {
//		List<Question> questionList = this.questionService.getList();
//		model.addAttribute("questionList", questionList);
//		return "question_list";
//	}

//	@GetMapping(value = "/question/detail/{id}")
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		// @PathVariable : http://localhost:8888/question/detail/2 의 숫자 2 처럼 변하는 id 값을
		// 얻을 때 사용한다.
		// 이때 @GetMapping(value = "/question/detail/{id}") 에서 사용한 id와
		// @PathVariable("id") 의 매개변수 이름이 동일 해야한다. (예제에서는 id로 작성하였기에 동일하다.)

		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}

	@PreAuthorize("isAuthenticated()") // 로그인 유저만 호출가능, 비로그인 유저 -> 로그인 페이지로 이동
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		// 탬플릿(html)의 form 태그에 th:object 속성을 추가했으므로 QuestionForm questionForm을 작성해준다.
		// question_form.html은 질문 등록하기 버튼을 통해 GET 방식으로 URL이 요청되더라도 th:object에 의해
		// QuestionForm 객체가 필요하기 때문이다.
		return "question_Form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		// subject, content 대신에 QuestionForm 객체로 매개변수를 변경하였다.
		// QuestionForm이 subject, content를 받고있기 때문에 거기서 가져올 수 있다.
		// 이름이 동일하면 함께 연결되어 묶이는 것이 폼의 binding 기능이다.
		// @Valid 를 적용하면 QuestionForm의 @NotEmpty @Size 등으로 설정한 검증 기능을 사용할 수 있다.
		// QuestionForm에서 바인드되어 가져와서 사용중인 subject content 의 검증 결과 가 BindingResult 이다.
		if (bindingResult.hasErrors()) {
			// 검증결과에 예외가 발생할 경우 입력 폼으로 다시 이동시킨다.
			return "question_form";
		}
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id")
	Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	// PreAuthorize 메소드 실행전에 검증한다. ( isAuthenticated() 로그인 상태 )
	@PostMapping("/modify/{id}")
	// modify 파일의 id 변수를 받는다.
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal, @PathVariable("id") Integer id)
	// @PathVariable = 해당 경로 (GetMapping에서 지정한)에서 변수 id를 가져오겠다.
	{	
		if (bindingResult.hasErrors()) {
		return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			// principal.getName() = 현재 인증된 사용자(principal)의 이름(getName())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
			// ResponseStatusException 클래스의 생성자를 호출해서 new를 통해 새로운 예외객체를 만들어낸다.
			// 이 때, 첫 번째 파라미터는 HttpStatus(Http응답상태로 코드 전달).BAD_REQUEST(400오류 - 잘못된 요청)
			// 두 번째 파라미터 "수정 권한이 없습니다."를 전달한다.
		}
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		
		return String.format("redirect:/question/detail/%s", id);
		// %s는 숫자이다.
	}
//	questionModify 메소드는 questionForm의 데이터를 검증하고 로그인한 사용자와 수정하려는 질문의 작성자가 동일한지도 검증한다.	
//	검증이 통과되면 QuestionService에서 작성한 modify 메소드를 호출하여 질문 데이터를 수정한다.
//	수정이 완료되면 질문 상제 화면 (/question/detail/(숫자))로 리다이렉트한다.
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
			// 로그인 사용자와 질문 작성자가 동일할 경우 삭제, 그렇지 않을 경우 경고창을 띄운다.
		}
		this.questionService.delete(question); // 해당 질문 삭제
		return "redirect:/"; // 질문을 삭제한 후 질문 목록 화면(/)으로 돌아간다.
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
}




//	@PostMapping("/create")
//	// question_form에서 post방식으로 데이터를 보내주고 있다.
//	public String questionCreate(@RequestParam(value="subject") String subject,
//			@RequestParam(value="content") String content) {
//		// 제목(subject)과 내용(content)를 매개변수로 받는다.
//		// 이 때 question_form.html에서 입력한 항목의 이름 (subject, content)가
//		// @RequestParam의 value 값과 동일해야한다.
//		
//		this.questionService.create(subject, content);
//		// questionService 클래스의 create() 메소드를 호출하여 질문 데이터(subject, content)를 저장한다.
//		
//		return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동

//	private final QuestionService questionService;
//	
//	@GetMapping("/question/list")
//	public String list(Model model) {
//		List<Question> questionList = this.questionService.getList();
//		model.addAttribute("questionList", questionList);
//		return "question_list";
//	}
// 이를 통해 Question 클래스를 직접 호출, 실행 시키는 것이 아니라
// QuestionController 클래스가 QuestionService를 호출,실행시키고
// QuestionService 클래스가 Question의 엔티티를 호출, 실행시키게 된다.

//	private final QuestionRepository questionRepository;
//	
//	@GetMapping("/question/list")
//	public String list(Model model) { // 매개변수로 Model을 지정하면 객체가 자동으로 생성된다.
//		List<Question> questionList = this.questionRepository.findAll();
//		// findAll() 메소드를 통해 질문 목록 데이터인 questionList 를 생성하고
//		model.addAttribute("questionList", questionList);
//		// Model 객체에 'questionlist' 라는 이름으로 저장했다. (여기서 Model 객체는 자바 클래스와 템플릿 간의 연결 고리 역할을 한다.)
//		// Model 객체에 값을 담아 두면 템플릿에서 그 값을 사용할 수 있다.
//		// Model 객체는 따로 생성할 필요 없이 컨트롤러의 메소드에 매개변수로 지정하기만 하면 스프링 부트가 자동으로 Model 객체를 생성한다.
//		return "question_list";
//	}

//	@GetMapping("/question/list")
//	public String list() {
//		return "question_list";
//		// @ResponseBody 어노테이션을 사용하고 있지 않기에 문자열 question_list 가 아니라
//		// 파일명 question_list 를 의미한다.
//		// 이로써 list() 메소드는 question_list.html(템플릿 파일)을 리턴한다.
//	}

//	@GetMapping("/question/list")
//	@ResponseBody // url 매핑이 아니라 해당 메소드의 String 값을 보여준다. 여기서는 문자열 question list
//	public String list() {
//		return "question list";
//		// 일반적으로는 직접 자바코드를 통해 작성하지 않는다.
//		// 템플릿 방식을 사용한다.
//		// 템플릿 : 자바 코드를 삽입할 수 있는 HTML 형식의 파일을 말한다.
//	}
