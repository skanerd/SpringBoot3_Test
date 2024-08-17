package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
// HelloController 클래스가 컨트롤러의 기능을 수행한다.
// 해당 어노테이션을 작성해줘야 스프링부트가 컨트롤러로 인식한다.
public class HelloController {
	@GetMapping("/hello")
	// 클라이언트의 요청으로 hello 메소드를 실행하게 될 것이다.
	
	// http://localhost:8888/hello
	// URL요청이 발생하면 hello 메소드가 호출(실행)되게 해준다.
	// port 8888 은 내가 임의로 수정한 것
	
//	@PostMapping("/hello")
	// 로 작성하면 get 방식이 아닌 post 방식이 된다.
	
	@ResponseBody
	// hello 메소드의 출력값 그대로 리턴하도록 한다.
	
	public String hello() {
		return "Hello World /// build.gradle에 developmentOnly 작성하면 실시간 적용 가능";
	}
}


// 실습 Jump.java