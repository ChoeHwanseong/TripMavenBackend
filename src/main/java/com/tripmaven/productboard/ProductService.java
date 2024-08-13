package com.tripmaven.productboard;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripmaven.csboard.CSBoardDto;
import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.members.service.MembersRepository;

import lombok.RequiredArgsConstructor;

// 상품게시판 등록 및 관리 
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	//members 리파지토리 주입
	private final MembersRepository membersRepository;
	
	//검색
	
	//게시글 전체 조회
	@Transactional
	public List<ProductBoardDto> listAll() {
		List<ProductBoardEntity> postEntityList = productRepository.findAll();
		return postEntityList.stream().map(user->ProductBoardDto.toDto(user)).collect(Collectors.toList());
	} //listAll() : 게시글 전체 조회
	
	// 게시글 제목으로 검색
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByTitle(String findTitle) {
		List<ProductBoardEntity> products = productRepository.findByTitleContaining(findTitle);
		List<ProductBoardDto> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductBoardDto.toDto(product));
		} 
		return productsDto;
	}
	
	// 게시물 내용으로 검색
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByContent(String findContent) {
		List<ProductBoardEntity> products=productRepository.findByContentContaining(findContent);
		List<ProductBoardDto> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductBoardDto.toDto(product));
		} 
		return productsDto;
	}
	
	// 게시글 도시로 찾기 
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByCity(String findCity) {
		List<ProductBoardEntity> products=productRepository.findByCityContaining(findCity);
		List<ProductBoardDto> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductBoardDto.toDto(product));
		} 
		return productsDto;
	}
	
	// 게시글 이메일로 찾기 
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByEmail(String email) {
	    MembersEntity membersEntity = membersRepository.findByEmail(email).orElse(null);
	    if (membersEntity!=null) {
	        List<ProductBoardEntity> entityList = productRepository.findByMember(membersEntity);
	        return entityList.stream().map(entity->ProductBoardDto.toDto(entity)).toList();
	    }
	    return null;
	}
	
	// 제목 + 내용
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByTitleAndContent(String keyword) {
		List<ProductBoardEntity> products = productRepository.findByTitleOrContentContaining(keyword, keyword);
		List<ProductBoardDto> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductBoardDto.toDto(product));
		} 
		return productsDto;
		
	}

	/////////////////////

	//게시글 수정
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

	////////////////////////////
	
	//게시글 삭제 여부
	@Transactional
	public ProductBoardDto delete(long id) {
		ProductBoardDto deletedDto=ProductBoardDto.toDto(productRepository.findById(id).get());
		productRepository.deleteById(id);
		return deletedDto;
	}

	
	///등록
	@Transactional
	public ProductBoardDto create(ProductBoardDto dto) {
		return ProductBoardDto.toDto(productRepository.save(dto.toEntity()));
	}

	public ProductBoardDto searchById(String id) {
		ProductBoardEntity entity = productRepository.findById(Long.parseLong(id)).orElse(null);
		return ProductBoardDto.toDto(entity);
	}
	
	/////////////////////
}
