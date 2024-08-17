package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@GetMapping("/ssb")
	@ResponseBody
	public String index() {
		return "안녕하세요 sbb에 오신것을 환영합니다.";
	}
	
	@GetMapping("/")
	public String root() {
		return "redirect:/question/list"; // redirect할 url도 입력해준다.
		// root() 메소드를 추가하고 url을 매핑하였다.
		// 여기서 redirect 란 클라이언트가 요청하면 새로운 url로 전송하는 것을 의미한다.
	}
}





//@Controller
//// 이로서 MainController은 스프링부트의 컨트롤러가 된다.
//public class MainController {
//	@GetMapping("/sbb")
//	
//	@ResponseBody
//	// ResponseBody 어노테이션을 하지 않으면 String 타입의 index를 http에 반환하는게 아니라 "index"라는 이름의 View을 찾게 된다.
//	// 어노테이션 한 메소드 (예제에서는 index()) 의 리턴값을 http에 그대로 반환해주는 기능이다.
//	// 여기서는 index() 메소드의 return값 String타입의 텍스트를 반환해주고 있다.
//	public String index() {
//		return "index를 반환한다.";
//	}
//}
