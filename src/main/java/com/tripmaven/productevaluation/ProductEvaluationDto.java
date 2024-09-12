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
	private int cheek_X;
	private String cheek_Y;
	private int mouth_X;
	private String mouth_Y;
	private int brow_X;
	private String brow_Y;
	private int eye_X;
	private String eye_Y;
	private int nasolabial_X;
	private String nasolabial_Y;

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
				.cheek_X(cheek_X)
				.cheek_Y(cheek_Y)
				.mouth_X(mouth_X)
				.mouth_Y(mouth_Y)
				.brow_X(brow_X)
				.brow_Y(brow_Y)
				.eye_X(eye_X)
				.eye_Y(eye_Y)
				.nasolabial_X(nasolabial_X)
				.nasolabial_Y(nasolabial_Y)
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
				.cheek_X(productEvaluationEntity.getCheek_X())
				.cheek_Y(productEvaluationEntity.getCheek_Y())
				.mouth_X(productEvaluationEntity.getMouth_X())
				.mouth_Y(productEvaluationEntity.getMouth_Y())
				.brow_X(productEvaluationEntity.getBrow_X())
				.brow_Y(productEvaluationEntity.getBrow_Y())
				.eye_X(productEvaluationEntity.getEye_X())
				.eye_Y(productEvaluationEntity.getEye_Y())
				.nasolabial_X(productEvaluationEntity.getNasolabial_X())
				.nasolabial_Y(productEvaluationEntity.getNasolabial_Y())
				.build();

	}


}
