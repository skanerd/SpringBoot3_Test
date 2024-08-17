package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/sam_ple")
@Controller
public class Practice_02_02_SampleController {
	
//	@GetMapping("/sam_ple/hell_o")
	@GetMapping("/hell_o")
	@ResponseBody
	public String hell_o() {
		return "Hell_o Sam_ple";
	}
}

//문제 1은 application.properties에 작성되어있다.
// p.173

// 상위 클래스에 @RequestMapping()에 이미 "/sam_ple" 이라고 경로가 작성되어있는데
// 하위 메소드에서 @GetMapping()으로 또 "sam_ple/hell_o" 라고 경로를 중복해서 잡아줬기에 404 오류가 발생하였다.
// @GetMapping("/hell_o") 라고 작성하고 localhost:8888/sam_ple/hell_o 라고 입력하면 잘 들어가진다. 