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
public class LikeyController {

	private final LikeyService likeyService;
	private final MembersService membersService;
	private final ProductService productService;
	private final ObjectMapper mapper;

	//찜 목록 전체 불러오기
	@CrossOrigin	
	@GetMapping("/likey/getAll")
	public ResponseEntity<List<LikeyDto>> getListAll(){
		try {
			List<LikeyDto> postList=likeyService.listAll();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(postList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	//게시글 찜하기
	@CrossOrigin
	@PostMapping("/product/likey/post")
	public ResponseEntity<LikeyDto> addWishList(@RequestParam Map<String,String> map){
		try {
			String members_id=map.get("members_id").toString();
			MembersEntity members = membersService.searchByMemberID(Long.parseLong(members_id)).toEntity();
			String productboard_id = map.get("productboard_id").toString();
			ProductBoardEntity productboard= productService.usersById(Long.parseLong(productboard_id)).toEntity();
			LikeyDto likeyDto = mapper.convertValue(map, LikeyDto.class);		
			likeyDto.setMember(members);
			likeyDto.setProductBoard(productboard);
			LikeyDto createWishList = likeyService.addtoWishList(likeyDto);
			return ResponseEntity.ok(createWishList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	//찜 삭제하기
	@CrossOrigin
	@DeleteMapping("/likey/remove/{productID}/{memberID}")
	public ResponseEntity<String> removeFromWishlist(
			@PathVariable Long productID,
			@PathVariable Long memberID) {
		try {
			LikeyDto dto=LikeyDto.builder()
					.productBoard(ProductBoardEntity.builder().id(productID).build())
					.member(MembersEntity.builder().id(memberID).build())
					.build();
			LikeyDto likeyDto = likeyService.deletetoWishList(dto);
			return ResponseEntity.ok("게시물이 찜 목록에서 제거되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("찜 제거에 실패했습니다.");
		}
	}
}//
