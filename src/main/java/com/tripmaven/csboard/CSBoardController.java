package com.tripmaven.csboard;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cs")
public class CSBoardController {
	
	private final CSBoardService csBoardService;
	private final ObjectMapper mapper;
	private final MembersService membersService;

	
	//CREATE (문의 등록)
	@PostMapping("/post")
	@CrossOrigin	
	public ResponseEntity<CSBoardDto> createInquire(@RequestParam Map map) {
		try {
			String members_id = map.get("members_id").toString();
			MembersEntity members =  membersService.searchByMemberID(Long.parseLong(members_id)).toEntity();
			CSBoardDto dto = mapper.convertValue(map, CSBoardDto.class);				
			dto.setMember(members);
			CSBoardDto createInquire = csBoardService.create(dto);	
			return ResponseEntity.ok(createInquire);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	

	//READ 관리자 측 전체 조회
	@GetMapping("/getAll")
	@CrossOrigin	
	public ResponseEntity<List<CSBoardDto>> getUsersAll(){
		try {
			List<CSBoardDto> inquireList= csBoardService.usersAll();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(inquireList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);			
			}		
	}
	
	


	
	// READ 사용자 등록 문의 전체 조회 (회원엔터티 FK_email로 조회)
	@GetMapping("/get/email/{email}")
	@CrossOrigin
	public ResponseEntity<List<CSBoardDto>> getInquireByUsername(@PathVariable("email") String email){
		try {
			MembersDto dto= csBoardService.usersByEmail(email);
			List<CSBoardDto> csDto= csBoardService.findAllById(dto.getId());
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(csDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);			
		}		
	}
	
		
	// READ 사용자 등록 문의 1개 조회(cs엔터티 PK_id로)
	@GetMapping("/get/{id}")
	@CrossOrigin
	public ResponseEntity<CSBoardDto> getInquireById(@PathVariable("id") Long id){
		try {
			CSBoardDto dto= csBoardService.usersById(id);
			return ResponseEntity.ok(dto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);			
		}		
	}
		
	
	
	//UPDATE (문의 수정)
	@PutMapping("/put/{id}")
	@CrossOrigin
	public ResponseEntity<CSBoardDto> updateInquire(@PathVariable("id") long id, @RequestParam Map map){
		try {
			CSBoardDto dto = mapper.convertValue(map, CSBoardDto.class);
			CSBoardDto updatedDto= csBoardService.updateById(id,dto);
			return ResponseEntity.ok(updatedDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);			
		}		
	}
	
	//UPDATE (문의 답변수정)
	@PutMapping("/answer/{id}")
	@CrossOrigin
	public ResponseEntity<CSBoardDto> updateAnswer(@PathVariable("id") long id, @RequestParam Map map){
		try {
			CSBoardDto dto = mapper.convertValue(map, CSBoardDto.class);
			CSBoardDto updatedDto= csBoardService.updateAnswerById(id,dto);
			return ResponseEntity.ok(updatedDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);			
		}		
	}

	
	//DELETE (문의 삭제)
	@DeleteMapping("/delete/{id}")
	@CrossOrigin
	public ResponseEntity<CSBoardDto> deleteInquire(@PathVariable("id") long id){
		try {
			CSBoardDto deletedDto= csBoardService.deleteById(id);
			return ResponseEntity.ok(deletedDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);			
		}		
	}
	
	
	
	// 문의 내용 검색
	// 문의 내용 검색 -제목
	@CrossOrigin
	@GetMapping("/title/{title}")
	public ResponseEntity<List<CSBoardDto>> getPostByTitle (@PathVariable("title") String title) {
		try {
			List<CSBoardDto> searchTitles=csBoardService.searchByTitle(title);
			return ResponseEntity.ok(searchTitles);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	// 문의 내용 검색 -내용	
	@CrossOrigin
	@GetMapping("/content/{content}")
	public ResponseEntity<List<CSBoardDto>> getPostByContent (@PathVariable("content") String content) {
		try {
			List<CSBoardDto> searchContents=csBoardService.searchByContent(content);
			return ResponseEntity.ok(searchContents);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	// 문의 내용 검색 -제목+내용 (잘 모르겠음)
	@CrossOrigin
	@GetMapping("/titlencontent/{keyword}")
	public ResponseEntity<List<CSBoardDto>> getPostsByTitleAndContent(@PathVariable("keyword") String keyword) {
		try {
			List<CSBoardDto> dtoList = csBoardService.searchByTitleOrContent(keyword);
			return ResponseEntity.ok(dtoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}


}
