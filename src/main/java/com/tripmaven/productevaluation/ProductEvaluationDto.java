package com.tripmaven.productevaluation;

import java.time.LocalDateTime;

import com.tripmaven.members.model.MembersEntity;
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
	private MembersEntity member;
	private ProductBoardEntity productBoard;
	private LocalDateTime createdAt;
	private String isDelete;
	private int score;
	private int pronunciation;
	private String tone;
	private int fillerwords;
	private int formal_speak;
	private int question_speak;
	private String text;
	private String weight;
	private String cheek;
	private String mouth;
	private String brow;
	private String eye;
	private String nasolabial;
	
	//DTO를 ENTITY로 변환하는 메소드
	public ProductEvaluationEntity toEntity() {
		return ProductEvaluationEntity.builder()
				.id(id)
				.member(member)
				.productBoard(productBoard)
				.createdAt(createdAt)
				.isDelete(isDelete)
				.score(score)
				.pronunciation(pronunciation)
				.tone(tone)
				.fillerwords(fillerwords)
				.formal_speak(formal_speak)
				.question_speak(question_speak)
				.text(text)
				.weight(weight)
				.cheek(cheek)
				.mouth(mouth)
				.brow(brow)
				.nasolabial(nasolabial)
				.eye(eye)
				.build();
	}

	//ENTITY를 DTO로 변환하는 메소드
	public static ProductEvaluationDto toDto(ProductEvaluationEntity productEvaluationEntity) {
		return ProductEvaluationDto.builder()
				.id(productEvaluationEntity.getId())
				.member(productEvaluationEntity.getMember())
				.productBoard(productEvaluationEntity.getProductBoard())
				.createdAt(productEvaluationEntity.getCreatedAt())
				.isDelete(productEvaluationEntity.getIsDelete())
				.score(productEvaluationEntity.getScore())
				.pronunciation(productEvaluationEntity.getPronunciation())
				.tone(productEvaluationEntity.getTone())
				.fillerwords(productEvaluationEntity.getFillerwords())
				.formal_speak(productEvaluationEntity.getFormal_speak())
				.question_speak(productEvaluationEntity.getQuestion_speak())
				.text(productEvaluationEntity.getText())
				.weight(productEvaluationEntity.getWeight())
				.cheek(productEvaluationEntity.getCheek())
				.mouth(productEvaluationEntity.getMouth())
				.brow(productEvaluationEntity.getBrow())
				.nasolabial(productEvaluationEntity.getNasolabial())
				.eye(productEvaluationEntity.getEye())
				.build();

	}


}
