package com.tripmaven.productevaluation;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.likey.LikeyDto;
import com.tripmaven.likey.LikeyService;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;
import com.tripmaven.productboard.ProductBoardEntity;
import com.tripmaven.productboard.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductEvaluationController {
	
	private final ProductService productService;
	private final MembersService membersService;
	private final ProductEvaluationService productEvaluationService;
	private final ObjectMapper mapper;
	
	// 평가 등록
	@CrossOrigin
	@PostMapping("/evaluation")
	public ResponseEntity<ProductEvaluationDto> createEvaluation(@RequestParam Map<String,String> map){
		try {
			String productboard_id = map.get("productboard_id").toString();
			System.out.println("productboard_id: "+productboard_id);
			ProductBoardEntity productboard= productService.usersById(Long.parseLong(productboard_id)).toEntity();
			System.out.println("productboard.getId: "+productboard.getId());
			System.out.println("productboard.getProductEvaluation: "+productboard.getProductEvaluation());
			ProductEvaluationDto productEvaluationDto = mapper.convertValue(map, ProductEvaluationDto.class);
			productEvaluationDto.setProductBoard(productboard);
			ProductEvaluationDto dto = productEvaluationService.create(productEvaluationDto);
			return ResponseEntity.ok(dto);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	// 평가 조회
	@CrossOrigin	
	@GetMapping("/evaluation")
	public ResponseEntity<List<ProductEvaluationDto>> getListAll(){
		try {
			List<ProductEvaluationDto> Evaluation=productEvaluationService.listAll();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(Evaluation);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

}
