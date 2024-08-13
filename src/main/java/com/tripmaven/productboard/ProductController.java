package com.tripmaven.productboard;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.csboard.CSBoardDto;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
public class ProductController  {
	
	private final ProductService productService;
	private final ObjectMapper mapper;
	private final MembersService membersService;
	
	/////////////////////
	
	//CREATE (등록)
		@PostMapping(path ="/product")
		@CrossOrigin	
		public ResponseEntity<ProductBoardDto> createInquire(@RequestParam Map map) {
			try {
				String member_id = map.get("member_id").toString();
				MembersEntity members =  membersService.searchByMemberID(Long.parseLong(member_id)).toEntity();
				ProductBoardDto dto = mapper.convertValue(map, ProductBoardDto.class);				
				dto.setMember(members);
				ProductBoardDto createInquire = productService.create(dto);	
				return ResponseEntity.ok(createInquire);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
	
	
	
	//게시글 전체 조회 
	@GetMapping("/product")
	public ResponseEntity<List<ProductBoardDto>> getListAll(){
		try {
			List<ProductBoardDto> postList=productService.listAll(); 
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(postList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}		
	}//////
	
	////////////////////////////////////
	
	//게시글 검색	
	//게시글 제목으로 검색  
	@CrossOrigin
	@GetMapping("/product/title/{findTitle}")
	public ResponseEntity<List<ProductBoardDto>> getPostByTitle (@PathVariable ("findTitle") String findTitle) {
		try {
			List<ProductBoardDto> dtos=productService.searchByTitle(findTitle);
			return ResponseEntity.ok(dtos);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}	
	
	//게시글 내용으로 검색
	@CrossOrigin
	@GetMapping("/product/content/{findContent}")
	public ResponseEntity<List<ProductBoardDto>> getPostByContent (@PathVariable("findContent") String findContent) {
		try {
			List<ProductBoardDto> dtos=productService.searchByContent(findContent);
			return ResponseEntity.ok(dtos);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	//게시글 도시로 검색
	@CrossOrigin
	@GetMapping("/product/city/{findCity}")
	public ResponseEntity<List<ProductBoardDto>> getPostByCity (@PathVariable("findCity") String findCity) {
		try {
			List<ProductBoardDto> dtos=productService.searchByCity(findCity);
			return ResponseEntity.ok(dtos);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	
	//게시글 (아이디가 아니라 이메일)로 검색 -> 테스트 안해봄 ㅇ///고치긴 했는데 이거 쓰는건가?
	@CrossOrigin
	@GetMapping("/product/member/{email}")
	public ResponseEntity<List<ProductBoardDto>> getPostByMemberId (@PathVariable("email") String email) {
		try {
			List<ProductBoardDto> dtoList = productService.searchByEmail(email);
			return ResponseEntity.ok(dtoList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	//게시글 아이디로 검색
		@CrossOrigin
		@GetMapping("/product/{id}")
		public ResponseEntity<ProductBoardDto> getPostById(@PathVariable String id) {
			try {
				ProductBoardDto dto = productService.searchById(id);
				return ResponseEntity.ok(dto);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		}
	
	
	
	//게시글 제목+내용으로 검색
	@CrossOrigin
	@GetMapping("/product/titlencontent/{keyword}") 
	public ResponseEntity<List<ProductBoardDto>> getPostsByTitleAndContent(@PathVariable("keyword") String keyword) {
		try {
			List<ProductBoardDto> dtoList = productService.searchByTitleAndContent(keyword);
			return ResponseEntity.ok(dtoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	///////////////////////////////////////////

	
	//수정
	@CrossOrigin
	@PutMapping("/product/{id}")
	public ResponseEntity<ProductBoardDto> postUpdate(@PathVariable("id") long id, @RequestParam Map map) {
		try {
			ProductBoardDto dto = mapper.convertValue(map, ProductBoardDto.class);
			ProductBoardDto updateDto=productService.update(id,dto);
			return ResponseEntity.ok(updateDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	////////////////////////////////////////
		
	//삭제 
	@CrossOrigin
	@DeleteMapping("/product/{id}")
	public ResponseEntity<ProductBoardDto> postDelete(@PathVariable("id") long id){
		try {
			ProductBoardDto deleteDTO = productService.delete(id);
			return ResponseEntity.ok(deleteDTO);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	
} //ProductController
