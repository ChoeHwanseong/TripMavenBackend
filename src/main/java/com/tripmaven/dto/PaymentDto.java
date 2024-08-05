package com.tripmaven.dto;

import java.time.LocalDateTime;



import com.tripmaven.repository.PaymentEntity;
import com.tripmaven.repository.ProductBoardEntity;


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
public class PaymentDto {

	//필드
	private long id;
	private ProductBoardEntity productBoard;
	private int price;
	private String payMethod;
	private LocalDateTime createdAt;
	private String isactive;

	//DTO를 ENTITY로 변환하는 메소드
	public PaymentEntity toEntity() {
		return PaymentEntity.builder()
				.id(id)
				.productBoard(productBoard)
				.price(price)
				.payMethod(payMethod)
				.createdAt(createdAt)
				.isactive(isactive)
				.build();
	}

	//ENTITY를 DTO로 변환하는 메소드
	public static PaymentDto toDto(PaymentEntity paymentEntity) {
		return PaymentDto.builder()
				.id(paymentEntity.getId())
				.productBoard(paymentEntity.getProductBoard())
				.price(paymentEntity.getPrice())
				.payMethod(paymentEntity.getPayMethod())
				.createdAt(paymentEntity.getCreatedAt())
				.isactive(paymentEntity.getIsactive())
				.build();

	}


}
