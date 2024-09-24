package com.tripmaven.productevaluation;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;
import com.tripmaven.productboard.ProductBoardEntity;
import com.tripmaven.productboard.ProductService;
import com.tripmaven.review.ReviewDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductEvaluationController {

	private final ProductService productService;
	private final MembersService membersService;
	private final ProductEvaluationService productEvaluationService;
	private final ObjectMapper mapper;

	// 분석내용 등록
	@CrossOrigin
	@PostMapping("/evaluation")
	public ResponseEntity<ProductEvaluationDto> createEvaluation(@RequestBody Map<String, Object> map) {
		try {
			
			//System.out.println("ai 평가 저장 컨트롤러 들어옴");
			
			String member_id = map.get("member_id").toString();
			//System.out.println("ai 평가 저장 컨트롤러 member_id"+member_id);
			
			MembersEntity members =  membersService.searchByMemberID(Long.parseLong(member_id)).toEntity();
			String productboard_id = map.get("productboard_id").toString();
			ProductBoardEntity productboard = productService.usersById(Long.parseLong(productboard_id)).toEntity();
			ProductEvaluationDto productEvaluationDto = mapper.convertValue(map, ProductEvaluationDto.class);
			productEvaluationDto.setMember(members);
			productEvaluationDto.setProductBoard(productboard);
			
			ProductEvaluationDto dto = productEvaluationService.create(productEvaluationDto);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// 분석내용 전체조회
	@CrossOrigin
	@GetMapping("/evaluation")
	public ResponseEntity<List<ProductEvaluationDto>> getEvaluationAll() {
		try {
			List<ProductEvaluationDto> evaluation = productEvaluationService.evaluationAll();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(evaluation);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	// 분석내용 회원아이디로 조회
	@GetMapping("/evaluation/{id}")
	public ResponseEntity<ProductEvaluationDto> getEvaluationById(@PathVariable("id") long id) {
		try {
			ProductEvaluationDto dto = productEvaluationService.usersById(id);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	// 분석내용 상품아이디로 조회
	@GetMapping("/evaluation/product/{productId}")
	public ResponseEntity<List<ProductEvaluationDto>> getEvaluationByProductId(@PathVariable("productId") long productId) {
		try {
			List<ProductEvaluationDto> dto = productEvaluationService.usersByProductId(productId);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	
	
	
	
}
