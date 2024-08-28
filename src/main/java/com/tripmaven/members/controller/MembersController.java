package com.tripmaven.members.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.service.MembersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MembersController {

	private final MembersService membersService;
	private final ObjectMapper mapper;
	
	//CREATE
	//회원가입
	@CrossOrigin
	@PostMapping("/signup")
	public ResponseEntity<MembersDto> signUp(@RequestBody Map map){ //RequestParam에서 Body로 변경
		try {
			//맵을 DTO로 변환하는 코드 (파라미터 명과 필드명을 일치시켜야 함 아마?)  
			//System.out.println(map.get("password"));
			MembersDto dto= mapper.convertValue(map, MembersDto.class);
			MembersDto insertedDto = membersService.signup(dto);
			if(insertedDto == null)	{
				Map<String, String> response = new HashMap<>();
			    response.put("message", "중복된 아이디입니다.");
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			return ResponseEntity.ok(insertedDto);			
		}
		catch (Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//가이드가 되어라!
	/** 라이센스 파일 업로드 필요.*/
	@PutMapping("/toguide")
	public ResponseEntity<MembersDto> toGuide(@RequestBody Map map){
		String guidelicense = map.get("guidelicense").toString();
		String introduce = map.get("introduce").toString();
		long membersId = (long)map.get("id");
		MembersDto findMember =  membersService.searchByMemberID(membersId);
		findMember.setIntroduce(introduce);
		findMember.setGuidelicense(guidelicense);
		findMember.setRole("GUIDE");
		System.out.println(findMember);
		return ResponseEntity.ok(membersService.toguide(findMember));
	}
	
	//관리자가 되어라!
	@PutMapping("/toadmin/{membersid}")
	public ResponseEntity<MembersDto> toAdmin(@PathVariable("membersid") String membersid){
		MembersDto findMember =  membersService.searchByMemberID(Long.valueOf(membersid));
		findMember.setRole("ADMIN");
		System.out.println(findMember);
		return ResponseEntity.ok(membersService.toguide(findMember));
	}
	
	//READ
	//모든 회원 조회
	@CrossOrigin
	@GetMapping("/members")	
	public ResponseEntity<List<MembersDto>> getMembersAll(){
		try {
			List<MembersDto> usersList = membersService.membersAll();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(usersList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	//모든 회원 이메일 검색
	@CrossOrigin
	@GetMapping("/members/email/{email}")
	public ResponseEntity<MembersDto> getMemberByMemberEmail (@PathVariable("email") String email) {
		try {
			MembersDto dto = membersService.searchByMemberEmail(email);
			return ResponseEntity.ok(dto);
		}
		catch(Exception e) {
			
			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	} 	
	
	//모든 회원 닉네임 검색
	@CrossOrigin
	@GetMapping("/members/name/{name}")
	public ResponseEntity<List<MembersDto>> getMemberByMemberName (@PathVariable("name") String name) {
		try {
			List<MembersDto> searchName = membersService.searchByMemberName(name);
			return ResponseEntity.ok(searchName);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	} 	
	
	//모든 회원 아이디 검색
	@CrossOrigin
	@GetMapping("/members/id/{id}")
	public ResponseEntity<MembersDto> getMemberByMemberId (@PathVariable("id") Long id){
		try {
			System.out.println(id);
			MembersDto dto = membersService.searchByMemberID(id);
			return ResponseEntity.ok(dto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
			
	//UPDATE
	//회원 정보 수정
	@CrossOrigin
	@PutMapping("/members/{id}")	
	public ResponseEntity<MembersDto> usersUpdate(@PathVariable("id") Long id,@RequestBody MembersDto dto){
		try {
			System.out.println(dto.getGuidelicense());
			System.out.println(id);
			MembersDto updatedDto = membersService.updateByMemberId(id,dto);
			return ResponseEntity.ok(updatedDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	//DELETE
	//회원 정보 삭제
	@CrossOrigin
	@DeleteMapping("/members/{id}")	
	public ResponseEntity<MembersDto> removeMemberByMembersId(@PathVariable("id") Long id){
		try {
			MembersDto deletedDto = membersService.deleteByMemberId(id);
			return ResponseEntity.ok(deletedDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
}
