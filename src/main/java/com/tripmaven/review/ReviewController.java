package com.tripmaven.review;

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
import com.tripmaven.productboard.ProductBoardDto;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost")
public class ReviewController {
	
	private final ReviewService reviewService;
	private final ObjectMapper mapper;
	private final MembersService membersService;

	
	//CREATE (리뷰 등록)
	@PostMapping("/post")	
	public ResponseEntity<ReviewDto> createInquire(@RequestParam Map map) {
		try {
			String members_id = map.get("members_id").toString();
			MembersEntity members =  membersService.searchByMemberID(Long.parseLong(members_id)).toEntity();
			ReviewDto dto = mapper.convertValue(map, ReviewDto.class);				
			dto.setMember(members);
			
			ReviewDto createReview = reviewService.create(dto);	
			return ResponseEntity.ok(createReview);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	


}
