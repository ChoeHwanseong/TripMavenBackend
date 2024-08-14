package com.tripmaven.likey;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.productboard.ProductBoardEntity;
import com.tripmaven.report.ReportDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikeyController {

	private final LikeyService likeyService;
	
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
	@PostMapping("/likey/{productID}/{memberID}")
	public ResponseEntity<String> likeyPost(
			@PathVariable("productID") Long productID,
			@PathVariable("memberID") Long memberID){
		try {
			
			LikeyDto dto=LikeyDto.builder()
					.productBoard(ProductBoardEntity.builder().id(productID).build())
					.member(MembersEntity.builder().id(memberID).build())
					.build();
			LikeyDto likeyDto = likeyService.addtoWishList(dto);
			if(likeyDto != null) {
			return ResponseEntity.ok("찜 목록에 추가 되었습니다.");
			}
			else {
				return ResponseEntity.ok("찜 등록에 실패하였습니다.");
			}
		}
		catch (Exception e) {
	         e.printStackTrace();
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("서버오류로 인해 찜 등록에 실패하였습니다.");
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
