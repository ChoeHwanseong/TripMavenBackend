package com.tripmaven.members;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembersService {

	private final MembersRepository membersRepository;
	private final ObjectMapper objectMapper;
	
	//READ
	//모든 회원 조회
	@Transactional(readOnly = true)
	public List<MembersDto> membersAll() {
		List<MembersEntity> membesrEntityList1 = membersRepository.findAll();
		return objectMapper.convertValue(membesrEntityList1, objectMapper.getTypeFactory().defaultInstance().constructCollectionType(List.class,MembersDto.class));
	}
	
	//모든 회원 이메일으로 검색
	@Transactional(readOnly = true)
	public MembersDto searchByMemberEmail(String email) {		
		return MembersDto.toDto(membersRepository.findByEmail(email).get());
	}
	
	//모든 회원 닉네임으로 검색
	@Transactional(readOnly = true)
	public List<MembersDto> searchByMemberName(String name) {		
		List<MembersEntity> membersEntityList2 = membersRepository.findByName(name);
		return objectMapper.convertValue(membersEntityList2, objectMapper.getTypeFactory().defaultInstance().constructCollectionType(List.class,MembersDto.class));
	}
	
	//모든 회원 아이디로 검색
	@Transactional(readOnly = true)
	public MembersDto searchByMemberID(Long id) {		
		return MembersDto.toDto(membersRepository.findById(id).get());
	}
	
	//UPDATE
	//회원 정보 수정
	@Transactional
	public MembersDto updateByMemberId(Long id, MembersDto dto) {
		MembersEntity members=membersRepository.findById(id).get();
		members.setName(dto.getName());
		members.setAddress(dto.getAddress());
		members.setBirthday(dto.getBirthday());
		members.setGender(dto.getGender());
		members.setTelNumber(dto.getTelNumber());
		return MembersDto.toDto(membersRepository.save(members));
		
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
