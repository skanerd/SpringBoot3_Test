package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;


@Component // CommonUtil 클래스는 스프링 부트가 관리하는 빈으로 등록된다.
public class CommonUtil {
	public String markdown(String markdown) {
		// markdown 메소드 생성
		// 마크다운 텍스트를 HTML 문서로 변환하여 리턴한다. 즉, 마크다운 문법이 적용된 일반 텍스트를 변환된 HTML로 리턴한다.
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);
	}
}
