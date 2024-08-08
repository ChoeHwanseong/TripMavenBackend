package com.tripmaven.productboard;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripmaven.members.MembersEntity;
import com.tripmaven.members.MembersRepository;

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
	
	// 게시글 이메일(회원고유번호가 아니라 이메일)로 찾기 
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByEmail(String email) {
		//member 리파지토리에서 멤버 엔터티를 가져와서
		//findByMember 메소드의 매개변수로 넣어줘야 함
		MembersEntity membersEntity = membersRepository.findByEmail(email).orElse(null);
		if(membersEntity != null) {
			List<ProductBoardEntity> entityList =  productRepository.findByMember(membersEntity);
			return entityList.stream().map(e -> ProductBoardDto.toDto(e)).toList();
		}
		return null;
	}
	
	// 제목 + 내용
	@Transactional(readOnly = true)
	public List<ProductBoardDto> searchByTitleAndContent(String keyword) {
		List<ProductBoardEntity> products=productRepository.findByTitleAndContent(keyword);
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
	public ProductBoardDto delete(Long id,ProductBoardDto dto) {
		ProductBoardEntity productBoardEntity=productRepository.findById(id).get();
		productBoardEntity.setIsUpdate("1"); //삭제여부
		productBoardEntity.setDeletedAt(LocalDateTime.now()); //삭제시간
		return ProductBoardDto.toDto(productRepository.save(productBoardEntity));
	}
	
	/////////////////////
}
