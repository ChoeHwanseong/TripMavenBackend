package com.tripmaven.auth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.tripmaven.repository.MembersEntity;
import com.tripmaven.repository.MembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private MembersRepository membersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//리포지토리 호출
		MembersEntity membersEntity= membersRepository.findById(username);
		//User는 UserDetails를 상속받은 Spring Security 에서 제공하는 클래스이다
		//물론 UserDetails를 직접 implements 받아서 구현해도 된다
		//직접 구현시에는 UserDetails인터페이스를 상속받아서 구현한후
		//return new MyUserDetails(users);

		//조회한 사용자 정보를 저장
		return MembersEntity
				.builder()
				.username(MembersEntity.getUsername())
				.password(MembersEntity.getPassword())
				.roles(MembersEntity.getRole())
				.build();
	}

}
