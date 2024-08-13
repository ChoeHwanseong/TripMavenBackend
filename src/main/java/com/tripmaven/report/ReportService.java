package com.tripmaven.report;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.csboard.CSBoardDto;
import com.tripmaven.csboard.CSBoardEntity;
import com.tripmaven.productboard.ProductBoardDto;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ReportService {
	
	private final ReportRepository reportRepository;  
	private final ObjectMapper objectMapper;
	

	//신고 등록
	@Transactional
	public ReportDto create(ReportDto reportDto) {
		return ReportDto.toDto(reportRepository.save(reportDto.toEntity()));
	}


    //전체 조회
	@Transactional(readOnly = true)
	public List<ReportDto> listAll() {
		List<ReportEntity> inquireEntityList= reportRepository.findAll();	
		// 엔터티 리스트를 dto 로 변환
		return objectMapper.convertValue(inquireEntityList,
										objectMapper.getTypeFactory().defaultInstance()
										.constructCollectionLikeType(List.class, ReportDto.class));
	}
	
}
