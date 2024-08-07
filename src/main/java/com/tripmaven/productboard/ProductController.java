package com.tripmaven.productboard;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
public class ProductController  {
	
	private final ProductService productService;
	/////////////////////
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
	} //게시글 전체 조회
		////////////////////////////////////
	//	게시글 검색	
		//게시글 제목으로 검색
		@CrossOrigin
		@GetMapping("/product/title/{findTitle}")
		public ResponseEntity<List<ProductBoardDto>> getPostByTitle (@PathVariable String findTitle) {
			try {
				List<ProductBoardDto> dtos=productService.searchByTitle(findTitle);
				return ResponseEntity.ok(dtos);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		}		
		// 게시글 내용으로 검색
		@CrossOrigin
		@GetMapping("/product/content/{findContent}")
		public ResponseEntity<List<ProductBoardDto>> getPostByContent (@PathVariable String findContent) {
			try {
				List<ProductBoardDto> dtos=productService.searchByContent(findContent);
				return ResponseEntity.ok(dtos);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		}			
		// 게시글 도시로 검색
		@CrossOrigin
		@GetMapping("/product/city/{findCity}")
		public ResponseEntity<List<ProductBoardDto>> getPostByCity (@PathVariable String findCity) {
			try {
				List<ProductBoardDto> dtos=productService.searchByCity(findCity);
				return ResponseEntity.ok(dtos);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		}		
		// 게시글 회원고유번호(아이디)로 검색
		@CrossOrigin
		@GetMapping("/product/member/{findMemberId}")
		public ResponseEntity<ProductBoardDto> getPostByMemberId (@PathVariable Long findMemberId) {
			try {
				ProductBoardDto dto=productService.searchByMemberId(findMemberId);
				return ResponseEntity.ok(dto);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} 		
		// 게시글 제목+내용으로 검색
		@CrossOrigin
		@GetMapping("/product/titlencontent/{keyword}")
		public ResponseEntity<List<ProductBoardDto>> getPostsByTitleAndContent(@PathVariable String keyword) {
			try {
				List<ProductBoardDto> dtoList = productService.searchByTitleAndContent(keyword);
				return ResponseEntity.ok(dtoList);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		}
/////////////////////////////////////////////////////////////////

		//수정
		@CrossOrigin
		@PutMapping("/product/{id}")
		public ResponseEntity<ProductBoardDto> postUpdate(@PathVariable Long id, @RequestBody ProductBoardDto dto) {
			try {
				System.out.println("게시글 수정 요청입니다.");
				ProductBoardDto updateDTO = productService.update(id,dto);
				return ResponseEntity.ok(updateDTO);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		}
		
		////////////////////////////////////////
		
		//삭제
		@CrossOrigin
		@PutMapping("/product/{id}")
		public ResponseEntity<ProductBoardDto> postDelete(@PathVariable Long id, @RequestBody ProductBoardDto dto){
			try {
				System.out.println("게시글 삭제 요청입니다.");
				ProductBoardDto deleteDTO = productService.delete(id,dto);
				return ResponseEntity.ok(deleteDTO);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		}
		 
		 
		
} //ProductController
