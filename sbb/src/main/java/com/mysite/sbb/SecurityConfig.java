package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 이 파일이 스프링의 환경 설정 파일임을 알려준다. (스프링 시큐리티 설정을 위해)
@EnableWebSecurity  // 모든 요청 url이 스프링 시큐리티의 제어를 받도록 한다. (스프링 시큐리티 활성화)
					// SecurityFilterChain이 모든 요청 url에 대해 작동하게된다.
					// CSRF 토큰을 자동생성해준다. create의 소스를 보면 된다.
@EnableMethodSecurity(prePostEnabled = true) // QuestionController, AnswerController 에서 로그인 여부를 판별할 때
											 // @PreAuthorize 어노테이션을 사용하기 위해 반드시 필요한 설정이다.

public class SecurityConfig {
	@Bean // bean을 생성하여 세부 설정을 해줄 수 있다.
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			
			.csrf((csrf) -> csrf
				.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
			// h2DB는 스프링과 관련이 없는 애플리케이션이기 때문에 토큰을 생성하지 못 한다.
			// h2-console로 시작하는 url에 대한 요청을 제외한다.
			
			.headers((headers) -> headers
				.addHeaderWriter(new XFrameOptionsHeaderWriter(
					XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
			// X-Frame-Options 헤더를 DENY에서 SAMEORIGIN으로 설정
			// 같은 도메인의 페이지 (sameorigin) 허용
			
			.formLogin((formLogin) -> formLogin
				.loginPage("/user/login")
				.defaultSuccessUrl("/"))
			
			.logout((logout) -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)) // 로그아웃 시 생성된 사용자 세션 삭제
			;
		
		return http.build();
		// 인증되지 않은 페이지의 요청을 허락한다는 의미이다.
		// 따라서 로그인하지 않더라도 페이지에 접근할 수 있도록 한다.
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	} // 비밀번호 암호화
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	} // 스프링 시큐리티의 인증을 처리한다. (인증과 권한 부여 프로세스)
}
