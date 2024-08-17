package com.mysite.sbb.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/signup") // 정보를 요청하는 get 방식일 때는 회원가입 폼
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup") // 정보를 생성/갱신하는 post 방식일 때는 회원가입을 진행한다.
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		} // 유효성 검사

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
			return "signup_form";
		} // bindingResult.rejectValue(필드명, 오류 코드, 오류 메세지)

		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
			// 중복된 데이터에 대한 예외 처리
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			// bindingResult.reject(오류코드, 오류메세지)
			// 그 외의 다른 예외가 발생하면 e.getMessage()로 오류 메세지를 출력한다.
			return "signup_form";
		}

		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
}
