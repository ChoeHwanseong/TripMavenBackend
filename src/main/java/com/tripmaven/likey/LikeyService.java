package com.tripmaven.likey;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeyService {

	private final LikeyRepository likeyRepository;
	
	// 찜 목록 전체 불러오기
	@Transactional
	public List<LikeyDto> listAll() {
		List<LikeyEntity> postEntityList=likeyRepository.findAll();
		return postEntityList.stream().map(user->LikeyDto.toDto(user)).collect(Collectors.toList());
	}
	
	// 게시글 찜하기
	@Transactional
	public LikeyDto addtoWishList(LikeyDto dto) {
		LikeyEntity likeyEntity= likeyRepository.save(dto.toEntity());
		return LikeyDto.toDto(likeyEntity);
	}
	
	// 게시글 찜 삭제하기
	@Transactional
	public LikeyDto deletetoWishList(LikeyDto dto) {
		// TODO Auto-generated method stub
		return null;
	}


}
