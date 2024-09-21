package com.tripmaven.guideranking;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GuideRankingRepository extends JpaRepository<GuideRankingEntity, Long> {
   
	// db랑 소통하는 애...
	// 총 리뷰수..... 흠..... 평균별점같을때 리뷰 많은애 먼저 띄우기 해야하나?
	
	
	
	
	
	
	
	
	
}