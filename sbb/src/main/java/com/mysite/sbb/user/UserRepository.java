package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	// UserRepository는 JpaRepository(CRUD)를 반드시 사용해야한다. (인터페이스)
	// 제네릭을 통해 UserRepository 인터페이스는
	// SiteUser(엔티티)를 다루고, 주 키(primary key)가 Long 타입을 사용할거라고 알려준다.
	
	Optional<SiteUser> findByUsername(String username);
	
}
