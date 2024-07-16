package com.tripmaven.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tripmaven.repository.MembersEntity;
import com.tripmaven.repository.MembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemversService {
	
	private final MembersRepository membersRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public boolean signup(MembersDTO dto) {
		
		boolean isDuplicated = membersRepository.existsByEmail(dto.getEMail());
		if(isDuplicated) return false;
		
		//암호화
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		MembersEntity entity = dto.toEntity();
		
		//역할 DTO에서 받아왔잖아~
		
		membersRepository.save(entity);
		return true;
		
	}

}
