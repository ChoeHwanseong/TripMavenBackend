package com.tripmaven.productboard;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tripmaven.likey.LikeyEntity;
import com.tripmaven.members.model.MembersEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductBoardEntity, Long> {

	// 회원고유번호로 찾기(회원아이디)
    List<ProductBoardEntity> findByMember(MembersEntity entity); 
    List<ProductBoardEntity> findByMemberIn(List<MembersEntity> entity);
    
	// 제목으로 찾기
	List<ProductBoardEntity> findByTitleContaining(String title);
	// 내용으로 찾기 
    List<ProductBoardEntity> findByContentContaining(String content);  
    // 도시로 찾기
    Page<ProductBoardEntity> findByCityContaining(String findCity, PageRequest of);
       
    // 제목과 내용 모두 포함하는 항목 찾기
    Page<ProductBoardEntity> findByTitleOrContentContaining(String keyword, String keyword2, PageRequest of);
  	
	// READ 가이드 측 게시글 가져오기(아이디로)
	List<ProductBoardEntity> findByMember_Id(long id);
	
	Page<ProductBoardEntity> findByTitleContainingOrContentContaining(String keyword, String keyword2, PageRequest of);

	
	

    
}
