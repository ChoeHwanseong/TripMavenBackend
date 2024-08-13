package com.tripmaven.report;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.productboard.ProductBoardEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	//// 여기서 리포트디티오를 만들어라 

	//전체
	@GetMapping("product/report")
	public ResponseEntity<List<ReportDto>> getListAll(){
		try {
			List<ReportDto> postList=reportService.listAll();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(postList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	
	//게시글 신고
	@CrossOrigin
	@PostMapping("/product/report/{productID}/{memberID}")
	public ResponseEntity<String> reportContent(
			@PathVariable("productID") Long productID,
			@PathVariable("memberID") Long memberID,
			@RequestParam Map<String,String> payload
			){
		try {
			ReportDto dto =ReportDto.builder()
					.productBoard(ProductBoardEntity.builder().id(productID).build())
					.member(MembersEntity.builder().id(memberID).build())
					.etc(payload.get("etc"))
					.attitude(payload.get("attitude"))
					.information(payload.get("information"))
					.disgust(payload.get("disgust"))
					.offensive(payload.get("offensive"))
					.noShow(payload.get("noShow"))
					.build();
			ReportDto reportDTO = reportService.reportContent(dto);
			if (reportDTO != null) {
				return ResponseEntity.ok("게시글 신고 완료");
			} //if
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("신고에 실패했습니다.");
			}//else
		} //try
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류로 인해 신고에 실패했습니다.");
		} //catch
	} 
	
	
}//////////////////////////////////////////////////////////////////

