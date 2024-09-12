package com.tripmaven.guideranking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuideRankingService {
    @Autowired
    private GuideRankingRepository guideRankingRepository;
    
    public List<GuideRankingDTO> getGuideRanking() {
        return guideRankingRepository.findGuideRanking();
    }
}