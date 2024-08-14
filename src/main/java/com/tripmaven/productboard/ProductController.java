package com.tripmaven.productboard;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.csboard.CSBoardDto;
import com.tripmaven.members.model.MembersDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
public class ProductController  {
	
	private final ProductService productService;
	private final ObjectMapper mapper;
	private final MembersService membersService;

	//CREATE (게시글 등록)
	@PostMapping("/product")
	@CrossOrigin	
	public ResponseEntity<ProductBoardDto> createPost(@RequestParam Map<String , String> map) {
		try {
			String members_id = map.get("members_id").toString();
			MembersEntity members =  membersService.searchByMemberID(Long.parseLong(members_id)).toEntity();
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
	
		
	
	//READ 관리자 측 전체 게시글 조회
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
	}

	
	

	// READ 가이드 측 게시글 조회(회원엔터티 FK_email로 조회)
	@CrossOrigin
	@GetMapping("/product/member/{email}")
	public ResponseEntity<List<ProductBoardDto>> getPostByEmail (@PathVariable("email") String email) {
		try {
			MembersDto dto= productService.usersByEmail(email);
			List<ProductBoardDto> csDto= productService.findAllById(dto.getId());
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(csDto);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	

	// READ 가이드 측 게시글 조회(cs엔터티 PK_id로)	
	@GetMapping("/product/{id}")
	public ResponseEntity<ProductBoardDto> getPostById(@PathVariable("id") Long id){
		try {
			ProductBoardDto dto= productService.usersById(id);
			return ResponseEntity.ok(dto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);			
		}		
	}
		
	
	
	//UPDATE (게시글 수정)
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
	
	//DELETE (게시글 삭제)
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
	
	
	//게시글 검색	
	//게시글 검색 -제목
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
	
	//게시글 검색 -내용	
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
	
	//게시글 검색 -도시
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
	

	//게시글 검색 -제목+내용
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

	
}
