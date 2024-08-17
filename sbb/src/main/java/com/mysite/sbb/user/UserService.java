package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password)); // 비밀번호 암호화
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		user.setPassword(passwordEncoder.encode(password));

		// BCryptPasswordEncoder 객체를 new를 통해 직접 생성하지 않고 Bean(SecurityConfig에 작성)에 등록한
		// PasswordEncoder 객체를 주입받아 사용하도록 수정.

		this.userRepository.save(user);
		return user;
	} // 유저 데이터 생성 (create 메소드)

	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		if (siteUser.isPresent()) { // Optional값을 isPresent()를 통해 유/무 확인한다. -> 유(true) 무(false)
			return siteUser.get(); // siteUser에는 Optional값이 들어있다. 그걸 get()으로 추출한다.
		} else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
}
