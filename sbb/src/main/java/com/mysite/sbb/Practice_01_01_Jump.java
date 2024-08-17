package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Practice_01_01_Jump {
	@GetMapping("/jump")
	@ResponseBody
	
	public String jump() {
		return "Welcome to SrpingBoot";
	}
}

// p.49 

// GetMapping()을 통해 localhost8888:/jump 라고 입력해도 들어갈 수 있게 해준다.
// ResponseBody 를 통해 해당 리턴 값을 바로 보여줄 수 있다.