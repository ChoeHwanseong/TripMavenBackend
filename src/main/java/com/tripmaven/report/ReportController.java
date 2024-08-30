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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.csboard.CSBoardDto;
import com.tripmaven.csboard.CSBoardService;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersService;
import com.tripmaven.productboard.ProductBoardDto;
import com.tripmaven.productboard.ProductBoardEntity;
import com.tripmaven.productboard.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ReportController {

	private final ReportService reportService;
	private final MembersService membersService;
	private final ProductService productService;
	private final ObjectMapper mapper;

	// 신고내역 조회 (관리자)
	@GetMapping("/product/report")
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
	

	// 신고내역 조회 (가이드 member_id로 신고내역에 값 뿌려줄때 )
	@GetMapping("/product/report/{member_id}")
	public ResponseEntity<ReportDto> getReportById(@PathVariable("member_id") Long member_id){
		try {
			ReportDto dto= reportService.reportsById(member_id);
			return ResponseEntity.ok(dto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);			
		}		
	}
	
		
	//게시글 신고
	@PostMapping("/product/report/post")
	public ResponseEntity<ReportDto> reportContent(@RequestBody Map<String, String> map){
		try {		
			String member_id = map.get("member_id").toString();
			MembersEntity members =  membersService.searchByMemberID(Long.parseLong(member_id)).toEntity();
			String productboard_id = map.get("productboard_id").toString();
			ProductBoardEntity productboard = productService.usersById(Long.parseLong(productboard_id)).toEntity();
			ReportDto reportDto = mapper.convertValue(map, ReportDto.class);				
			reportDto.setMember(members);
	        reportDto.setProductBoard(productboard);;
	        ReportDto createdReport = reportService.create(reportDto);	
	        return ResponseEntity.ok(createdReport);
		} //try
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} //catch
	} 
	
	
}//////////////////////////////////////////////////////////////////

