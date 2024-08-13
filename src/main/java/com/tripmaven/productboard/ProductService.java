package com.tripmaven.productboard;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.csboard.CSBoardDto;
import com.tripmaven.csboard.CSBoardEntity;
import com.tripmaven.members.model.MembersDto;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final MembersRepository membersRepository;
	private final ObjectMapper objectMapper;

	//CREATE (게시글 등록)
	@Transactional
	public ProductBoardDto create(ProductBoardDto dto) {
		return ProductBoardDto.toDto(productRepository.save(dto.toEntity()));
	}
	
	

	//READ 관리자 측 전체 게시글 조회
	@Transactional(readOnly = true)
	public List<ProductBoardDto> listAll() {
		// 리포지토리 호출
		List<ProductBoardEntity> postEntityList= productRepository.findAll();	
		// 엔터티 리스트를 dto 로 변환
		return objectMapper.convertValue(postEntityList,
										objectMapper.getTypeFactory().defaultInstance()
										.constructCollectionLikeType(List.class, ProductBoardDto.class));
	}
	
	
	// READ 가이드 측 게시글 조회(회원엔터티 FK_email로 조회)
	@Transactional(readOnly = true)
	public MembersDto usersByEmail(String email) {
		return MembersDto.toDto(membersRepository.findByEmail(email).get());
	}
		
	// READ 가이드 측 게시글 가져오기
	@Transactional(readOnly = true)
	public List<ProductBoardDto> findAllById(long id) {
		// 리포지토리 호출
		List<ProductBoardEntity> postEntityList= productRepository.findByMember_Id(id);	
		// 엔터티 리스트를 dto 로 변환
		return objectMapper.convertValue(postEntityList,
										objectMapper.getTypeFactory().defaultInstance()
										.constructCollectionLikeType(List.class, ProductBoardDto.class));
	}
	
	
	// READ 가이드 측 문의내역 조회(cs엔터티 PK_id로)	
	public ProductBoardDto usersById(Long id) {
		return ProductBoardDto.toDto(productRepository.findById(id).get());
	}
	
	
	
	//UPDATE (게시글 수정)
	@Transactional
	public ProductBoardDto update(Long id,ProductBoardDto dto) {
		ProductBoardEntity productBoardEntity=productRepository.findById(id).get();
		
		productBoardEntity.setTitle(dto.getTitle());
		productBoardEntity.setContent(dto.getContent());
		productBoardEntity.setCity(dto.getCity());
		productBoardEntity.setIsUpdate("1"); //수정여부
		productBoardEntity.setUpdatedAt(LocalDateTime.now()); //수정시간
		
		return ProductBoardDto.toDto(productRepository.save(productBoardEntity));
	}
	
	//DELETE (게시글 삭제)
	@Transactional
	public ProductBoardDto delete(long id) {
		ProductBoardDto deletedDto=ProductBoardDto.toDto(productRepository.findById(id).get());
		productRepository.deleteById(id);
		return deletedDto;
	}
	

	
	
	//게시글 검색	
	//게시글 검색 -제목
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByTitle(String findTitle) {
		List<ProductBoardEntity> products = productRepository.findByTitleContaining(findTitle);
		List<ProductBoardDto> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductBoardDto.toDto(product));
		} 
		return productsDto;
	}

	public CSBoardDto searchByProductId(long long1) {
		
		return null;
	}
	
	//게시글 검색 -내용	
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByContent(String findContent) {
		List<ProductBoardEntity> products=productRepository.findByContentContaining(findContent);
		List<ProductBoardDto> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductBoardDto.toDto(product));
		} 
		return productsDto;
	}
	
	//게시글 검색 -도시
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByCity(String findCity) {
		List<ProductBoardEntity> products=productRepository.findByCityContaining(findCity);
		List<ProductBoardDto> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductBoardDto.toDto(product));
		} 
		return productsDto;
	}
	

	//게시글 검색 -제목+내용
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByTitleAndContent(String keyword) {
		List<ProductBoardEntity> products = productRepository.findByTitleOrContentContaining(keyword, keyword);
		List<ProductBoardDto> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductBoardDto.toDto(product));
		} 
		return productsDto;
	}


}
