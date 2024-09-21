package com.tripmaven.guideranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.tripmaven.members.model.MembersEntity;
import com.tripmaven.review.ReviewEntity;
import com.tripmaven.review.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuideRankingService {

	// 리뷰 리포지에서 가이드별 리뷰들 조회
	private final ReviewRepository reviewRepository;

	// 평균 별점을 계산하고 랭킹 순서대로 가이드 정보 가져오기
	@Transactional // (readOnly = true) 왜 안되지 ?... 
	public List<GuideRankingDto> calculateGuideRankings() {
		List<GuideRankingDto> rankings = new ArrayList<>();

		// 모든 리뷰 조회
		List<ReviewEntity> reviews = reviewRepository.findAll();

		// 가이드별로 리뷰를 그룹화
		Map<MembersEntity, List<ReviewEntity>> guideReviews = reviews.stream()
				.collect(Collectors.groupingBy(ReviewEntity::getMember));

		// 가이드별로 평균 별점 계산
		for (Map.Entry<MembersEntity, List<ReviewEntity>> entry : guideReviews.entrySet()) {
			MembersEntity guide = entry.getKey();
			List<ReviewEntity> guideReviewList = entry.getValue();

			// 리뷰의 별점을 합산하고 평균 계산
			double sum = guideReviewList.stream()
					.mapToDouble (ReviewEntity::getRatingScore)
					.sum();
			double average = guideReviewList.size() > 0 ? sum / guideReviewList.size() : 0.0;
			long totalReviews = guideReviewList.size();

			// DTO로 변환 후 리스트에 추가
			rankings.add(new GuideRankingDto(guide.getId(), guide, average, totalReviews));
		}

		// 평균 별점으로 내림차순 정렬 후 반환
		return rankings.stream()
				.sorted(Comparator.comparing(GuideRankingDto::getAverageRating).reversed())
				.collect(Collectors.toList());
	}
}
