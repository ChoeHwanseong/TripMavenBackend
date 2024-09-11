package com.tripmaven.review;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.csboard.CSBoardDto;
import com.tripmaven.csboard.CSBoardEntity;
import com.tripmaven.report.ReportDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	private final ObjectMapper objectMapper;
	
	// CREATE(리뷰 등록)
	@Transactional
	public ReviewDto create(ReviewDto dto) {
		return ReviewDto.toDto(reviewRepository.save(dto.toEntity()));
	}
	
	
	// READ (상품id 로 리뷰들 조회)
	@Transactional(readOnly = true)
	public List<ReviewDto> reviewByProductId(long productBoard) {
		// 리포지토리 호출
		List<ReviewEntity> reviewEntityList= reviewRepository.findAllByProductBoard_id(productBoard);	
		// 엔터티 리스트를 dto 로 변환
		return objectMapper.convertValue(reviewEntityList,
										objectMapper.getTypeFactory().defaultInstance()
										.constructCollectionLikeType(List.class, ReviewDto.class));
	}

		
	// READ (회원id 로 리뷰들 조회)
	@Transactional(readOnly = true)
	public ReviewDto reviewById(long id) {
		return ReviewDto.toDto(reviewRepository.findById(id).orElse(new ReviewEntity()));
	}
	
	// UPDATE (리뷰 수정)
	@Transactional
	public ReviewDto updateById(long id, ReviewDto dto) {
		ReviewEntity reviewEntity= reviewRepository.findById(id).orElse(new ReviewEntity());
		reviewEntity.setTitle(dto.getTitle());
		reviewEntity.setComments(dto.getComments());
		return ReviewDto.toDto(reviewRepository.save(reviewEntity));
	}
	
	// DELETE (리뷰 삭제)
	public ReviewDto deleteById(long id) {
		ReviewDto deletedDto= ReviewDto.toDto(reviewRepository.findById(id).get());
		reviewRepository.deleteById(id);
		return deletedDto;
	}
	

}
