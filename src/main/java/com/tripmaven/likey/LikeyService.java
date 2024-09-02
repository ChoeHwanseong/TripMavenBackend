package com.tripmaven.likey;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.csboard.CSBoardDto;
import com.tripmaven.productboard.ProductBoardDto;
import com.tripmaven.productboard.ProductBoardEntity;
import com.tripmaven.productboard.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeyService {

	private final LikeyRepository likeyRepository;
	private final ProductRepository productRepository;
	private final ObjectMapper objectMapper;
	
	// 게시글 찜하기
	@Transactional
	public LikeyDto addtoWishList(LikeyDto dto) {
		LikeyEntity likeyEntity= likeyRepository.save(dto.toEntity());
		return LikeyDto.toDto(likeyEntity);
	}
	
	// 게시글 찜 삭제하기
	@Transactional
	public boolean deletetoWishList(LikeyDto dto) {
		LikeyEntity likeyEntity = likeyRepository.findByMemberAndProductBoard(dto.getMember(),dto.getProductBoard());
		if (likeyEntity != null) {
		    likeyRepository.delete(likeyEntity);
		    return true;
		}
		return false;
	}
	
	
	//회원번호로 찜 가져오기  (내 찜 목록 시 사용)
	@Transactional(readOnly = true)
	public List<LikeyDto> findAllById(long memberID) {
		// 리포지토리 호출
		List<LikeyEntity> likeyEntityList= likeyRepository.findByMember_Id(memberID);	
		// 엔터티 리스트를 dto 로 변환
		return objectMapper.convertValue(likeyEntityList,
										objectMapper.getTypeFactory().defaultInstance()
										.constructCollectionLikeType(List.class, LikeyDto.class));
	}

}
