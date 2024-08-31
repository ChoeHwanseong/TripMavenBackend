package com.tripmaven.likey;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;
import com.tripmaven.productboard.ProductBoardEntity;
import com.tripmaven.productboard.ProductService;
import com.tripmaven.report.ReportDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class LikeyController {

	private final LikeyService likeyService;
	private final MembersService membersService;
	private final ProductService productService;
	private final ObjectMapper mapper;

	//게시글 별 찜가져오기는 양방향으로 설계해서 필요 없음
	//게시글 가져올때 같이 가져와짐
	
	//게시글 찜하기
	@PostMapping("/likey/{productId}/{memberId}")
	public ResponseEntity<LikeyDto> addWishList(
			@PathVariable Long productId,
			@PathVariable Long memberId){
		try {
			MembersEntity member = membersService.searchByMemberID(memberId).toEntity();
			ProductBoardEntity productboard= productService.usersById(productId).toEntity();
			LikeyDto likeyDto = LikeyDto.builder().member(member).productBoard(productboard).build();
			LikeyDto createdLikeyDto = likeyService.addtoWishList(likeyDto);
			return ResponseEntity.ok(createdLikeyDto);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	//찜 삭제하기
	@DeleteMapping("/likey/{productId}/{memberId}")
	public ResponseEntity<String> removeFromWishlist(
			@PathVariable Long productId,
			@PathVariable Long memberId) {
		try {
			MembersEntity member = membersService.searchByMemberID(memberId).toEntity();
			ProductBoardEntity productboard= productService.usersById(productId).toEntity();
			LikeyDto likeyDto = LikeyDto.builder().member(member).productBoard(productboard).build();
			boolean isDelete = likeyService.deletetoWishList(likeyDto);
			return ResponseEntity.ok(String.valueOf(isDelete));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("찜 제거에 실패했습니다.");
		}
	}
}//
