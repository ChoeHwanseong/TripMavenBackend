package com.tripmaven.productevaluation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.likey.LikeyDto;
import com.tripmaven.likey.LikeyEntity;
import com.tripmaven.members.service.MembersRepository;
import com.tripmaven.productboard.ProductBoardDto;
import com.tripmaven.productboard.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductEvaluationService {
	
	
	private final productEvaluationRepository productevaluationRepository;
	
	// 평가 등록
	public ProductEvaluationDto create(ProductEvaluationDto dto) {
		return ProductEvaluationDto.toDto(productevaluationRepository.save(dto.toEntity()));
	}
	
	// 평가 조회
	public List<ProductEvaluationDto> listAll() {
		List<ProductEvaluationEntity> evaluations=productevaluationRepository.findAll();
		return evaluations.stream().map(evaluation->ProductEvaluationDto.toDto(evaluation)).collect(Collectors.toList());
	}


}
