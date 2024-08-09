package com.tripmaven.csboard;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CSBoardRepository extends JpaRepository<CSBoardEntity, Long>{


	// 문의 내용 검색
	// 문의 내용 검색 -제목
	List<CSBoardEntity> findByTitleContaining(String findTitle);

	// 문의 내용 검색 -내용
	List<CSBoardEntity> findByContentContaining(String findContent);

	// 문의 내용 검색 -제목+내용
	List<CSBoardEntity> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
	
	// READ 사용자 등록 문의 내역 가져오기
	List<CSBoardEntity> findByMember_Id(long id);
	
	
	
	
	
	
	

	
	



    
}
