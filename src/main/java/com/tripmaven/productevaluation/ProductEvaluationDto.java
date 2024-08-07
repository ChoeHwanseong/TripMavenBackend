package com.tripmaven.productevaluation;

import java.time.LocalDateTime;

import com.tripmaven.productboard.ProductBoardEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEvaluationDto {


	//필드
	private long id;
	private ProductBoardEntity productBoard;
	private LocalDateTime createdAt;
	private Integer score;
	private String comments;

	//DTO를 ENTITY로 변환하는 메소드
	public ProductEvaluationEntity toEntity() {
		return ProductEvaluationEntity.builder()
				.id(id)
				.productBoard(productBoard)
				.createdAt(createdAt)
				.score(score)
				.comments(comments)
				.build();
	}

	//ENTITY를 DTO로 변환하는 메소드
	public static ProductEvaluationDto toDto(ProductEvaluationEntity productEvaluationEntity) {
		return ProductEvaluationDto.builder()
				.id(productEvaluationEntity.getId())
				.productBoard(productEvaluationEntity.getProductBoard())
				.createdAt(productEvaluationEntity.getCreatedAt())
				.score(productEvaluationEntity.getScore())
				.comments(productEvaluationEntity.getComments())
				.build();

	}


}
