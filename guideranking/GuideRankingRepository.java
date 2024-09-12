package com.tripmaven.guideranking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.tripmaven.members.model.MembersEntity;

@Repository
public interface GuideRankingRepository extends JpaRepository<MembersEntity, Long> {
    @Query("SELECT new com.tripmaven.guideranking.GuideRankingDTO(m.id, m.name, COUNT(p.id)) " +
           "FROM com.tripmaven.members.model.MembersEntity m " +
           "LEFT JOIN com.tripmaven.productboard.ProductBoardEntity p ON m.id = p.member.id " +
           "GROUP BY m.id, m.name ORDER BY COUNT(p.id) DESC")
    List<GuideRankingDTO> findGuideRanking();
}