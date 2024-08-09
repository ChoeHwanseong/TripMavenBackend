package com.tripmaven.csboard;

import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.members.MembersDto;
import com.tripmaven.members.MembersEntity;
import com.tripmaven.members.MembersRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CSBoardService {
	
	private final CSBoardRepository csBoardRepository;
	private final MembersRepository membersRepository;
	private final ObjectMapper objectMapper;
	
	//CREATE (문의 등록)
	@Transactional
	public CSBoardDto create(CSBoardDto dto) {
		return CSBoardDto.toDto(csBoardRepository.save(dto.toEntity()));
	}
	
	
	//READ 관리자 측 전체 조회
	@Transactional(readOnly = true)
	public List<CSBoardDto> usersAll() {
		// 리포지토리 호출
		List<CSBoardEntity> inquireEntityList= csBoardRepository.findAll();	
		// 엔터티 리스트를 dto 로 변환
		return objectMapper.convertValue(inquireEntityList,
										objectMapper.getTypeFactory().defaultInstance()
										.constructCollectionLikeType(List.class, CSBoardDto.class));
	}
	

	// READ 사용자 등록 문의 전체 조회(email로 조회)
	@Transactional(readOnly = true)
	public MembersDto usersByEmail(String email) {
		return MembersDto.toDto(membersRepository.findByEmail(email).get());
	}
	// READ 사용자 등록 문의 내역 가져오기
	@Transactional(readOnly = true)
	public List<CSBoardDto> findAllById(long id) {
		// 리포지토리 호출
		List<CSBoardEntity> inquireEntityList= csBoardRepository.findByMember_Id(id);	
		// 엔터티 리스트를 dto 로 변환
		return objectMapper.convertValue(inquireEntityList,
										objectMapper.getTypeFactory().defaultInstance()
									.constructCollectionLikeType(List.class, CSBoardDto.class));
	}
	
	

	// READ 사용자 등록 문의 1개 조회(문의 아이디로)
	@Transactional(readOnly = true)
	public CSBoardDto usersById(Long id) {		
		return CSBoardDto.toDto(csBoardRepository.findById(id).get());
	}

	
	//UPDATE (문의 수정)
	@Transactional
	public CSBoardDto updateById(long id, CSBoardDto dto) {
		CSBoardEntity csBoardEntity= csBoardRepository.findById(id).get();
		
		csBoardEntity.setTitle(dto.getTitle());
		csBoardEntity.setContent(dto.getContent());
		
		return CSBoardDto.toDto(csBoardRepository.save(csBoardEntity));
	}

	
	//DELETE (문의 삭제)
	@Transactional
	public CSBoardDto deleteById(long id) {
		CSBoardDto deletedDto= CSBoardDto.toDto(csBoardRepository.findById(id).get());
		csBoardRepository.deleteById(id);
		return deletedDto;
	}
	
	
	
	// 문의 내용 검색
	// 문의 내용 검색 -제목
	@Transactional
	public List<CSBoardDto> searchByTitle(String findTitle) {
		List<CSBoardEntity> inquireTitles = csBoardRepository.findByTitleContaining(findTitle);
		List<CSBoardDto> csDto = new Vector<>();
		
		for(CSBoardEntity inquireTitle : inquireTitles) {
			csDto.add(CSBoardDto.toDto(inquireTitle));
		}
		return csDto;
	}
	
	// 문의 내용 검색 -제목
	@Transactional
	public List<CSBoardDto> searchByContent(String findContent) {
		List<CSBoardEntity> inquireContents = csBoardRepository.findByContentContaining(findContent);
		List<CSBoardDto> csDto = new Vector<>();
		
		for(CSBoardEntity inquireContent : inquireContents) {
			csDto.add(CSBoardDto.toDto(inquireContent));
		}
		return csDto;
	}

	// 문의 내용 검색 -제목+내용
	@Transactional
	public List<CSBoardDto> searchByTitleOrContent(String keyword) {
	    List<CSBoardEntity> inquireTCs = csBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword);
	    List<CSBoardDto> csDto = new Vector<>();
	    
	    for(CSBoardEntity inquireTC : inquireTCs) {
			csDto.add(CSBoardDto.toDto(inquireTC));
		} 
		return csDto;
		
	}

	
	
	
	
	
}
