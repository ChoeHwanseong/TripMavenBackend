package com.tripmaven.review;

import org.springframework.stereotype.Service;

import com.tripmaven.csboard.CSBoardDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	
	//CREATE (리뷰 등록)
	public ReviewDto create(ReviewDto dto) {
		return ReviewDto.toDto(reviewRepository.save(dto.toEntity()));
	}
	
	

}
