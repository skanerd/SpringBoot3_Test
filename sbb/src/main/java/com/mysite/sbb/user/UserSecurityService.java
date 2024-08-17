package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
// 시큐리티를 위해 반드시 UserDetailsService를 구현(implement)한다.
// loadUserByUsername 메소드를 구현하기 위해.
// username으로 스프링 시큐리티의 사용자User 객체를 조회하여 리턴하는 메소드이다.
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
		// Optional로 <SiteUser>의 값 존재유무를 확인한다. (
		if (_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		SiteUser siteUser = _siteUser.get();
		// get()을 통해 _siteUser 안에 담긴 Optional(값이 존재) 값(SiteUser)을 가져와서 
		// 변수 siteUSer에 담아준다.
		List<GrantedAuthority> authorities = new ArrayList<>();
		// 사용자 권한 정보를 나타내는 GrantedAuthority 객체를 생성하는 데 사용할 리스트를 생성.
		if ("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
		// 이렇게 만들어진 User 객체는 비밀번호 일치 여부 확인 기능을 내장하고 있다.
	}
}
