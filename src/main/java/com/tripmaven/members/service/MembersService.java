package com.tripmaven.members.service;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.model.MembersEntity;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembersService {

	private final MembersRepository membersRepository;
	private final ObjectMapper objectMapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	//CREATE
	//회원가입
	@Transactional
	public MembersDto signup(MembersDto dto) {
		
		boolean isDuplicated = membersRepository.existsByEmail(dto.getEmail()); //증복확인
		if(!isDuplicated) {
			//암호화
			dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
			//역할 DTO에서 받아왔잖아~
			
			return MembersDto.toDto(membersRepository.save(dto.toEntity()));
		}
		else {
			MembersDto findMember = MembersDto.toDto(membersRepository.findByEmail(dto.getEmail()).get());
			boolean isSocial = findMember.getLoginType()!=null && !findMember.getLoginType().equalsIgnoreCase("local") ; //소셜인지 확인.
			if(isDuplicated && !isSocial) return null;
			if(isSocial) {
				findMember.setName(dto.getName());
				findMember.setPassword(dto.getPassword());
				findMember.setInterCity(dto.getInterCity());
				findMember.setGender(dto.getGender());
				findMember.setBirthday(dto.getBirthday());
				findMember.setAddress(dto.getAddress());
				dto=findMember;
				
			}
			//암호화
			dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
			//역할 DTO에서 받아왔잖아~
			
			return MembersDto.toDto(membersRepository.save(dto.toEntity()));
		}
	}
	
	
	
	//READ
	//모든 회원 조회
	@Transactional(readOnly = true)
	public List<MembersDto> membersAll() {
		List<MembersEntity> membesrEntityList1 = membersRepository.findAll();
		return objectMapper.convertValue(membesrEntityList1, objectMapper.getTypeFactory().defaultInstance().constructCollectionType(List.class,MembersDto.class));
	}
	
	//한사람 회원 이메일으로 검색
	@Transactional(readOnly = true)
	public MembersDto searchByMemberEmail(String email) {		
		return MembersDto.toDto(membersRepository.findByEmail(email).orElse(null));
	}
	
	//모든 회원 닉네임으로 검색
	@Transactional(readOnly = true)
	public List<MembersDto> searchByMemberName(String name) {		
		List<MembersEntity> membersEntityList2 = membersRepository.findByName(name);
		return objectMapper.convertValue(membersEntityList2, objectMapper.getTypeFactory().defaultInstance().constructCollectionType(List.class,MembersDto.class));
	}
	
	//회원 아이디로 검색
	@Transactional(readOnly = true)
	public MembersDto searchByMemberID(Long id) {
		System.out.println(id);
		return MembersDto.toDto(membersRepository.findById(id).orElse(null));
	}
	
	//로그인 아이디를 통해 멤버 DTO구하기
	public MembersDto getLoginMemberByLoginId(String loginId) {
		if(loginId == null) return null;
		MembersEntity membersEntity= membersRepository.findByEmail(loginId).get();
		return MembersDto.toDto(membersEntity);
	}
	
	
	//UPDATE
	//회원 정보 수정
	@Transactional
	public MembersDto updateByMemberId(Long id, MembersDto dto) {
		MembersEntity members=membersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
		
		if (dto.getName() != null) members.setName(dto.getName());
	    if (dto.getAddress() != null) members.setAddress(dto.getAddress());
	    if (dto.getBirthday() != null) members.setBirthday(dto.getBirthday());
	    if (dto.getGender() != null) members.setGender(dto.getGender());
	    if (dto.getTelNumber() != null) members.setTelNumber(dto.getTelNumber());
	    if (dto.getGuidelicense() != null) members.setGuidelicense(dto.getGuidelicense());
		return MembersDto.toDto(membersRepository.save(members));
	}
	
	//가이드 변환 로직
	@Transactional
	public MembersDto toguide(MembersDto findMember) {
		return MembersDto.toDto(membersRepository.save(findMember.toEntity()));
	}
	
	//DELETE
	//회원 정보 삭제
	@Transactional
	public MembersDto deleteByMemberId(Long id) {
		MembersDto deletedDto=MembersDto.toDto(membersRepository.findById(id).get());
		membersRepository.deleteById(id);
		return deletedDto;
	}



	



	
}
