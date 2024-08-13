package com.tripmaven.report;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripmaven.productboard.ProductBoardDto;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ReportService {
	
	private final ReportRepository reportRepository;  
	
	
	//전체
	@Transactional
	public List<ReportDto> listAll(){
		List<ReportEntity> postEntityList=reportRepository.findAll();
		return postEntityList.stream().map(user->ReportDto.toDto(user)).collect(Collectors.toList());
	}
	
	
	
	//신고
	
	
	@Transactional
	public ReportDto reportContent(ReportDto dto) {									
			ReportEntity  reportEntity = reportRepository.save(dto.toEntity());
			return ReportDto.toDto(reportEntity);
		
	}
	
}
