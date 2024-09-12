package com.tripmaven.productevaluation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductEvaluationService {

	private final productEvaluationRepository productevaluationRepository;

	// 분석내용 등록
	public ProductEvaluationDto create(ProductEvaluationDto dto) {
		return ProductEvaluationDto.toDto(productevaluationRepository.save(dto.toEntity()));
	}

	// 분석내용 전체조회
	public List<ProductEvaluationDto> evaluationAll() {
		List<ProductEvaluationEntity> evaluations = productevaluationRepository.findAll();
		return evaluations.stream().map(evaluation -> ProductEvaluationDto.toDto(evaluation))
				.collect(Collectors.toList());
	}

	// 분석내용 회원아이디로 조회
	public ProductEvaluationDto usersById(Long id) {
		return ProductEvaluationDto.toDto(productevaluationRepository.findById(id).get());
	}

}
